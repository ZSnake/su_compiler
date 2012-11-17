package Ada95_Semantic;

public abstract class PrimitiveType extends Type{
	PrimitiveType(){
		super();
	}
	
    @Override
	public boolean isPrimitive(){
		return true;
	}
}
