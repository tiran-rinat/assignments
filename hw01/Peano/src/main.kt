abstract class Peano {
    object Zero : Peano() {}
}
open class LoL (val value : Peano) : Peano () {}

fun Peano.Plus (a : Peano): Peano
{
    when(this)
    {
        is Peano.Zero  -> return a
        is LoL         -> return LoL(this.value.Plus(a))
        else           -> throw Exception("Unknown class")
    }
}

fun Peano.Minus (a : Peano): Peano
{
    when(this)
    {
        is Peano.Zero -> return Peano.Zero
        is LoL        ->
        {
            when (a)
            {
                is LoL         -> return this.value.Minus(a.value)
                is Peano.Zero  -> return this
                else           -> throw Exception("Unknown class")
            }
        }
        else          -> throw Exception("Unknown class")
    }
}

fun Peano.Mult (a : Peano): Peano
{
    when(this)
    {
        is Peano.Zero -> return Peano.Zero
        is LoL        ->
        {
            when (a)
            {
                is Peano.Zero -> return a
                is LoL        ->
                {
                    when(this.value)
                    {
                        is Peano.Zero -> return a
                        is LoL        -> return (a.Plus(this.value.Mult(a)))
                        else          -> throw Exception("Unknown class")
                    }
                }
                else          -> throw Exception("Unknown class")
            }
        }
        else          -> throw Exception("Unknown class")
    }
}

fun Peano.Pow (a : Peano): Peano
{
    when(a)
    {
        is Peano.Zero -> return LoL(Peano.Zero)
        is LoL        -> return this.Mult(this.Pow(a.value))
        else          -> throw Exception("Unknown class")
    }
}

fun Peano.ToInt (): Int
{
    when(this)
    {
        is Peano.Zero -> return 0
        is LoL        -> return 1 + this.value.ToInt()
        else          -> throw Exception("Unknown class")
    }
}

fun main (args: Array<String>)
{
    var bar = LoL(Peano.Zero)
    println("Default: " + bar.ToInt())

    bar = bar.Plus(LoL(Peano.Zero)) as LoL
    println("After fun Plus(LoL(Peano.Zero)): " + bar.ToInt())

    bar = bar.Mult(LoL(LoL(Peano.Zero))) as LoL
    println("After fun Mult(LoL(LoL(Peano.Zero))): " + bar.ToInt())

    bar = bar.Minus(LoL(Peano.Zero)) as LoL
    println("After fun Minus(LoL(Peano.Zero)): " + bar.ToInt())

    bar = bar.Pow(bar) as LoL
    println("After fun Pow(LoL(LoL(LoL(Peano.Zero)))): " + bar.ToInt())

    bar = bar.Pow(Peano.Zero) as LoL
    println("After fun Pow(Peano.Zero): " + bar.ToInt())
}