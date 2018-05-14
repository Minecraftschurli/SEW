package au16a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EMailAdr
{
    public static void main(String[]args)
    {
        System.out.println(new EMailAdr("gburkl@student.tgm.ac.at").toString());
    }

    public static final List<String> DOMAINS = new ArrayList<>();
    private String value;
    private boolean hasBeenSet;

    public EMailAdr(String mailAdr)
    {
        DOMAINS.addAll(Arrays.asList("com","at","tv","de","net","org","uk"));
        if(this.isValidEmail(mailAdr))
        {
            this.value = mailAdr;
            this.hasBeenSet = true;
        }
        else
        {
            this.value = "";
            this.hasBeenSet = false;
        }
    }

    private boolean isValidEmail(String mailAdr)
    {
        if (mailAdr.contains("@") && mailAdr.indexOf("@") == mailAdr.lastIndexOf("@"))
        {
            String domain = mailAdr.substring(mailAdr.indexOf("@")+1,mailAdr.length());
            System.out.println(domain);
            String topLevelDomain = domain.substring(domain.lastIndexOf(".")+1,domain.length());
            System.out.println(topLevelDomain);
            return DOMAINS.contains(topLevelDomain);
        }
        return false;
    }

    public String value()
    {
        return this.hasBeenSet ? value : "has not been set";
    }

    @Override
    public String toString() {
        return this.value;
    }
}
