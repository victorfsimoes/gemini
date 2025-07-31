package br.com.space.spacewebII.modelo.util.teste;

public class StringBuilderTeste {
	
	public static void main(String[] args) {
		StringBuilder x = new StringBuilder("x: ").append("00 ");
		StringBuilder y = new StringBuilder("y: " + "00 ");
		String z = "z: " + "00 ";
		
		for(int i = 0; i < 3; i++){
			x.append("> ").append(i); 
			y.append("> " + i);
			z += "> " + i;
		}
		
		StringBuilder a = new StringBuilder("a: ").append("11 ");
		StringBuilder b = new StringBuilder("b: " + "11 ");
		String c = "c: " + "11 ";
		boolean test = true;
		if(test){
			a.append("> ").append(" 11"); 
			b.append("> " + " 11");
			c += "> " + " 11";
		}
		
		a.append("> ").append(" 11"); 
		b.append("> " + " 11");
		c += "> " + " 11";
			
		
		System.out.println(x.toString());
		System.out.println(y.toString());
		System.out.println(z);
		System.out.println(a.toString());
		System.out.println(b.toString());
		System.out.println(c);
		
	}

}
