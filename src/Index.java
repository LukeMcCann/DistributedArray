import net.jini.core.entry.Entry;

public class Index implements Entry
{
    public String arrayName;
    public Integer position;

    public int increment()
    {
        int pos = position.intValue();
        position = new Integer(position.intValue() + 1);
        return pos;
    }

    public int decrement()
    {
        int pos = position.intValue();
        position = new Integer(position.intValue() - 1);
        return pos;
    }


}
