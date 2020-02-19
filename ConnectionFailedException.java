package com.assignment3.employee;

public class ConnectionFailedException extends Exception
{
	@Override
	public String getMessage() 
	{
		return "data base not connected";
	}

	@Override
	public void printStackTrace()
	{
		super.printStackTrace();
	}

}
