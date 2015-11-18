public class queryObject{
   
   String main_query;
   String conditional_query;
   String type;
   int id
   int meta_id;

   public void readIn()
   {
   //read in from sqlite DB here and set variables
   }
   //public String generateQuerry()
   //{
   
   
   //}
    public queryObject()
	{
	}
   
   public queryObject(String main, String newType, String conditional, int newId, int meta)
   {
		main_query = main;
		type = newType;
		conditional_query = conditional;
		id = newId;
		meta_id = meta;
   }
   public String getMainquery()
   {
		return main_query;
   }
   
   public String getType()
   {
		return type;
   }
   
   public String getConditionalQuery()
   {
		return conditional_query;
   }
   
   public int getID()
   {
		return id;
   }
   
   public int getMetaID()
   {
		return meta_id;
   }
   
   public void setMainquery(arg)
   {
		main_query = arg;
   }
   
   public void setType(arg)
   {
		type = arg;
   }
   
   public void setConditionalQuery(arg)
   {
		conditional_query = arg;
   }
   
   public void setID(arg)
   {
		id = arg;
   }
   
    public void setMetaID(arg)
   {
		meta_id = arg;
   }
}