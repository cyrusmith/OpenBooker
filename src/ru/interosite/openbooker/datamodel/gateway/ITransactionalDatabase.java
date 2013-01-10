package ru.interosite.openbooker.datamodel.gateway;

public interface ITransactionalDatabase {
	public void transactionBegin();
	public void transactionSuccessful();
	public void transactionEnd();
}