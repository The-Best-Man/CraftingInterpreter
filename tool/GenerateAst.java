package chapter6.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst
{
	public static void main(String[] args) throws IOException
	{
		if(args.length != 1)
		{
			System.err.println("Usage: generate_ast <output directory>");
			System.exit(64);
		}
		String outputDir = args[0];
		defineAst(outputDir, "Expr", Arrays.asList
				(
				 "Binary   : Expr left, Token operator, Expr right",
				 "Grouping : Expr expression",
				 "Literal  : Object value",
				 "Unary    : Token operator, Expr right"
				));
	}

	private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException
	{
		String path = outputDir + "/" + baseName + ".java";
		PrintWriter writer = new PrintWriter(path, "UTF-8");

		writer.println("package chapter6.lox;");
		writer.println();
		writer.println("import java.util.List;");
		writer.println();
		writer.println("abstract class " + baseName + '\n' + "{");
			
		// Base Visitor class
		defineVisitor(writer, baseName, types);
		
		for (String type : types)
		{
			String className = type.split(":")[0].trim();
			String fields = type.split(":")[1].trim();
			defineType(writer, baseName, className, fields);
		}

		// Accept class
		writer.println();
		writer.println("\t abstract <R> R accept(Visitor<R> visitor);");

		writer.println("}");
		writer.close();
	}

	private static void defineVisitor(PrintWriter writer, String baseName, List<String> types)
	{
		writer.println("\tinterface Visitor<R>\n\t{");

		for (String type : types)
		{
			String typeName = type.split(":")[0].trim();
			writer.println("\t\tR visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase() + ");");
		}

		writer.println("\t}");
	}

	private static void defineType(PrintWriter writer, String baseName, String className, String fieldList)
	{
		writer.println("\tstatic class " + className + " extends " + baseName + "\n\t{");

		writer.println("\t\t" + className + "(" + fieldList + ")\n\t\t{");

		String[] fields = fieldList.split(", ");
		for (String field : fields)
		{
			String name = field.split(" ")[1];
			writer.println("\t\tthis." + name + " = " + name + ";");
		}

		writer.println("\t\t}");

		// Vistor
		writer.println();
		writer.println("\t\t@Override");
		writer.println("\t\t<R> R accept(Visitor<R> visitor)\n\t\t{");
		writer.println("\t\t\treturn visitor.visit" + className + baseName + "(this);");
		writer.println("\t\t}");

		// Fields
		writer.println();
		for (String field : fields)
		{
			writer.println("\tfinal " + field + ";");
		}

		writer.println("\t}\n");
	}
}
