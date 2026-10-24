/**
 * Represents a symbol of the slot machine.
 * Each symbol is defined by a type and a color.
 * The color identifies the symbol and distinguishes it
 * from the other symbols in the machine.
 *
 * The visual representation of the symbol is handled by
 * the wheel that displays it.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 1.0
 */
public class Symbol
{
    private TypeSymbol tipo;
    private String color;


    /**
     * Creates a new symbol with the specified type and color.
     *
     * @param tipo the type of the symbol
     * @param color the color of the symbol
     */
    public Symbol(TypeSymbol tipo, String color)
    {
        this.tipo = tipo;
        this.color = color;
    }


    /**
     * Returns the type of this symbol.
     *
     * @return the type of the symbol
     */
    public TypeSymbol getType()
    {
        return tipo;
    }


    /**
     * Returns the color of this symbol.
     *
     * @return the color of the symbol
     */
    public String getColor()
    {
        return color;
    }


    /**
     * Returns a textual representation of this symbol.
     * The representation contains its type and color.
     *
     * @return a string containing the type and color
     */
    public String toString()
    {
        return tipo + " (" + color + ")";
    }
}

