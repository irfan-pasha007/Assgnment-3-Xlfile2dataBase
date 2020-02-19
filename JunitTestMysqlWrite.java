package com.assignment3.employee;


import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class JunitTestMysqlWrite {

	@Test
	void test()
	{	
	assertNull(ReadAndWriteToDataBase.connect("root","Irfpas123"));
	}
	@Test
	void test1()
	{	
	assertNotNull(ReadAndWriteToDataBase.connect("root","Irfpas123*"));
	}
	@Test
	void test2() throws Exception
	{	
	assertNotNull(ReadAndWriteToDataBase.readXlFile());
	}
	

	 
	

}
