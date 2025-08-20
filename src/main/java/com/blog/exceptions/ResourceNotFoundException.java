package com.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	private String resourceName ;
	private String fieldName;
	private long fieldValue;
	private static String value;
	public  ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %d: ", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public  ResourceNotFoundException(String resourceName, String fieldName, String Value) {
		super(String.format("%s not found with %s : %s: ", resourceName,fieldName,value));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.value = value;
	}

}
