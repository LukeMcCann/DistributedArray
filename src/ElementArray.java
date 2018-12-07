
import net.jini.space.JavaSpace05;

public class ElementArray
{
    private Integer THREE_MINUTES = (3 * 1000)*60;
    private Integer FIVE_MINUTES = (5 * 10000)*60;

    private JavaSpace05 space;
    private String arrayName;

    public static void main(String[] args)
    {
        JavaSpace05 space = SpaceUtils.getSpace();

        try
        {
            String name = "Test Array";
            ElementArray array = new ElementArray(space, name);

            array.create();

            for(int i = 0; i < 10; i++)
            {
                array.append(new Integer(i));
            }

            System.out.println("Array Size: " + array.getSize());

            for(int i = 0; i < 10; i++)
            {
                Integer elem = (Integer) array.readElement(i);
                System.out.println("Elem: " + i + "is: " + elem);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ElementArray(JavaSpace05 space, String arrayName)
    {
        this.space = space;
        this.arrayName = arrayName;
    }

    /*
     * Create the distributed array
     */
    public void create()
    {
        try
        {
            Start start = new Start();
            start.arrayName = arrayName;
            start.position = new Integer(0);

            End end = new End();
            end.arrayName = arrayName;
            end.position = new Integer(0);

            space.write(start, null, THREE_MINUTES);
            space.write(end, null, THREE_MINUTES);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public int append(Object obj)
    {
        int position = -1;
        try
        {
            End endTemplate = new End();
            endTemplate.arrayName = arrayName;

            End end = (End) space.take(endTemplate, null, 0);
            position = end.increment();
            space.write(end, null, THREE_MINUTES);

            Element element = new Element(arrayName, position, obj);
            space.write(element, null, THREE_MINUTES);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return position;
    }

    public int getSize()
    {
        Start start = null;
        End end = null;

        try
        {
            Start startTemplate = new Start();
            startTemplate.arrayName = arrayName;

            End endTemplate = new End();
            endTemplate.arrayName = arrayName;

            start = (Start) space.read(startTemplate, null, THREE_MINUTES);

            end = (End) space.read(endTemplate, null, THREE_MINUTES);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return (end.position.intValue() - start.position.intValue());
    }

    public Object readElement(int pos)
    {
        Element element = null;
        try
        {
            Element eleTemplate = new Element(arrayName, pos, null);

            element = (Element)
                    space.read(eleTemplate, null, THREE_MINUTES);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return element.data;
    }
}
