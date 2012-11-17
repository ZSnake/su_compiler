package Ada95_Semantic;

public class TypeTest{
	private static void typeCompare(Type a, Type b){
		String s =(a.equals(b)) ? " " : " no ";
		System.out.println("El tipo " + a.toString() + s + "es igual al tipo " + b.toString());
	}
	public static void main(String[] args){
		Type intg = new IntegerType();
                Type intg1 = new IntegerType("int");
                Type intg2 = new IntegerType("INTEGER");
                Type intg3 = new IntegerType();
		Type flt = new FloatType();
                Type flt1 = new FloatType("real");
                Type flt2 = new FloatType("FLOAT");
                Type flt3 = new FloatType();
		Type booln = new BooleanType();
		Type booln1 = new BooleanType("bool");
		Type booln2 = new BooleanType("BOOLEAN");
		Type booln3 = new BooleanType();

		typeCompare(intg, intg1);	
		typeCompare(intg, intg2);
		typeCompare(intg, intg3);
		typeCompare(flt, flt1);
		typeCompare(flt, flt2);
		typeCompare(flt, flt3);
		typeCompare(booln, booln1);
		typeCompare(booln, booln2);		
		typeCompare(booln, booln3);
	}
}
