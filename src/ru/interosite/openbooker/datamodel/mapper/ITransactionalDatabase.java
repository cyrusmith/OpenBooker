package ru.interosite.openbooker.datamodel.mapper;

public interface ITransactionalDatabase {
	public void transactionBegin();
	public void transactionSuccessful();
	public void transactionEnd();
}