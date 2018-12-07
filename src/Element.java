import net.jini.core.entry.Entry;

public class Element implements Entry
{
    public Integer index;
    public String arrayName;
    public Object data;

    public Element() {}

    public Element(String arrayName, int index, Object data)
    {
        this.arrayName = arrayName;
        this.index = index;
        this.data = data;
    }
}
