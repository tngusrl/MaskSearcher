package com.euphorbia.masksearcher;

public class Data {

    private String address;

    private Stores[] stores;

    private String count;

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public Stores[] getStores ()
    {
        return stores;
    }

    public void setStores (Stores[] stores)
    {
        this.stores = stores;
    }

    public String getCount ()
    {
        return count;
    }

    public void setCount (String count)
    {
        this.count = count;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [address = "+address+", stores = "+stores+", count = "+count+"]";
    }
}
