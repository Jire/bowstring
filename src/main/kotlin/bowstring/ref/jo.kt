package bowstring.ref

import java.lang.reflect.*

public abstract class TypeToken<T> {
	public val type: Type

	protected constructor() {
		this.type = extractType()
	}

	private fun extractType(): Type {
		val t = javaClass.genericSuperclass

		if (t !is ParameterizedType)
			throw RuntimeException("Invalid TypeToken; must specify type parameters")

		if (t.rawType != TypeToken::class.java)
			throw RuntimeException("Invalid TypeToken; must directly extend TypeToken")

		return t.actualTypeArguments[0]?:t
	}
}

public data class Key(
		val bind: Bind,
		val argType: Type
) {
	override fun toString() = buildString {
		if (bind.tag != null) append("\"${bind.tag}\": ")
		if (argType != Unit.javaClass) append("(${argType.dispName})") else append("()")
		append("-> ${bind.type.dispName}")
	}
}

public class TypeBinder<T : Any>(val bind: Bind) {
	//public infix fun <R : T, A> with(factory: Factory<A, R>) = _builder.bind(Key(bind, factory.argType), factory)
}

public data class Bind(
		val type: Type,
		val tag: Any?
) {
	//override fun toString() = "bind<${type.dispName}>(${ if (tag != null) "\"$tag\"" else "" })"
}

private var hasTypeName = true

public val Type.dispName: String get() {
	if (hasTypeName)
		try {
			return typeName
		}
		catch (ignored: NoSuchMethodError) {
			hasTypeName = false
		}
		catch (ignored: Throwable) {}

	if (this is Class<*>)
		return this.name

	return toString()
}

public inline fun <reified T : Any> bind(tag: Any? = null): TypeBinder<T> = TypeBinder(Bind(typeToken<T>(), tag))

inline fun <reified T: Any> a() = typeToken<T>()

private var _needPTWrapperCache: Boolean? = null;

/**
 * Detectes whether KodeinParameterizedType is needed.
 */
public fun _needPTWrapper(): Boolean {
	if (_needPTWrapperCache == null)
		_needPTWrapperCache = (object : TypeToken<List<String>>() {}).type != (object : TypeToken<List<String>>() {}).type
	return _needPTWrapperCache!!
}

class JoType(val type: Type)

public inline fun <reified T> canada() = JoType(typeToken<T>())

public inline fun <reified T> typeToken(): Type {
	val type = (object : TypeToken<T>() {}).type

	println(type)
	println(type.typeName)
	println(type.dispName)

	if (type is ParameterizedType && _needPTWrapper())
		return KodeinParameterizedType(type)

	if (type !is ParameterizedType && type !is Class<*>)
		throw RuntimeException("Invalid TypeToken; must specify type parameters")

	println(type)

	return type
}

public class KodeinParameterizedType(public val type: ParameterizedType) : Type {

	private var _hashCode: Int = 0;

	override fun hashCode(): Int {
		if (_hashCode == 0)
			_hashCode = Func.HashCode(type)
		return _hashCode
	}

	override fun equals(other: Any?): Boolean {

		val otherType =
				if (other is KodeinParameterizedType) other
				else if (other is ParameterizedType) KodeinParameterizedType(other)
				else return false

		if (hashCode() != otherType.hashCode())
			return false

		return Func.Equals(this.type, otherType.type)
	}

	override fun toString(): String {
		return "ParameterizedType $type"
	}

	private object Func {
		fun HashCode(type: Type): Int {
			if (type is Class<*>)
				return type.hashCode()

			if (type !is ParameterizedType)
				throw RuntimeException("Invalid TypeToken; must specify type parameters")

			var hashCode = HashCode(type.rawType);
			for (arg in type.actualTypeArguments)
				hashCode *= 31 + HashCode(if (arg is WildcardType) arg.upperBounds[0] else arg)
			return hashCode
		}

		fun Equals(left: Type, right: Type): Boolean {
			if (left is Class<*> && right is Class<*>)
				return left == right

			if (left is WildcardType)
				return Equals(left.upperBounds[0], right)
			if (right is WildcardType)
				return Equals(left, right.upperBounds[0])

			if (left !is ParameterizedType || right !is ParameterizedType)
				return false

			if (!Equals(left.rawType, right.rawType))
				return false;

			val leftArgs = left.actualTypeArguments
			val rightArgs = right.actualTypeArguments
			if (leftArgs.size != rightArgs.size)
				return false;

			for (i in leftArgs.indices)
				if (!Equals(leftArgs[i], rightArgs[i]))
					return false

			return true
		}
	}
}