package au16a;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author georg
 *
 */
public class Person 
{
	public static List<Person> PERSONEN;
	
	//Attribute
	private final Date geburtsdatum;
	private final String emailAdr;
	private List<EMail> postEingang;
	private List<EMail> postAusgang;
	private List<EMail> postEingangGelesen;
	
	//Konstruktor
	public Person(Date geburtsdatum, String emailAdr)
	{
		this.postAusgang = new ArrayList<>();
		this.postEingang = new ArrayList<>();
		this.postEingangGelesen = new ArrayList<>();
		this.geburtsdatum = geburtsdatum;
		this.emailAdr = emailAdr;
		Person.PERSONEN.add(this);
	}
	
	//Methoden
	public void sendeEMail(String empfAdr, String betreff, String nachricht)
	{
		EMail eMail = new EMail(this.emailAdr,empfAdr,betreff,nachricht);
		this.postAusgang.add(eMail);
		for(Person person : PERSONEN)
		{
			if(person.getEmailAdr().equals(empfAdr))
			{
				person.empfangeEMail(eMail);
				System.out.println("Email gesendet!");
				return;
			}
		}
		System.out.println("ERROR! Email empfänger existiert nicht!");
	}

	public void empfangeEMail(EMail eMail) 
	{
		this.postEingang.add(eMail);
	}
	
	public void leseEMail(String betreff)
	{
		for (EMail eMail : this.postEingang) 
		{
			if(eMail.getBetreff().equals(betreff))
			{
				System.out.println(eMail.toString());
				return;
			}
		}
		System.out.println("ERROR! Eine Email mit diesem Betreff ("+betreff+") existiert nicht!");
	}
	
	public String getEmailAdr() 
	{
		return this.emailAdr;
	}
}
