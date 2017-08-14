package com.janbask.training3;
import java.util.*;

/*
    Indexed Properties Example
    Author: Pradeep Singh
*/

public class IndexedProperty {

    // a vector that contains the actual stock names
    protected Vector stocks = new Vector();

    // constructor
    public IndexedProperty()
    {
    }

    // the get method for the StockCount property
    public synchronized int getStockCount()
    {
        // the StockCount property is derived from the size of the
        // stocks Vector
        return stocks.size();
    }

    // get method for Stocks property array
    public synchronized String[] getStocks()
    {
        // we don't currently deal with the case where the watch list
        // is empty

        // allocate an array of strings for the stock names
        String[] s = new String[getStockCount()];

        // copy the elements of the stocks Vector into the string array,
        // and then return the array
        stocks.copyInto(s);
        return s;
    }

    // set method for Stocks property array
    public synchronized void setStocks(String[] s)
    {
        // the existing list of stocks is removed in favor of the
        // new set of stocks

        // set the size of the stocks vector to match the length
        // of the new array
        stocks.setSize(s.length);

        // copy the values into the stocks vector
        for (int i = 0; i < s.length; i++)
        {
            // use the single stock set method
            try
            {
                setStocks(i, s[i]);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
            }
        }
    }

    // get method for single element of Stocks property
    public synchronized String getStocks(int index)
            throws ArrayIndexOutOfBoundsException
    {
        // make sure the index is in bounds
        if (index < 0 || index >= getStockCount())
        {
            throw new ArrayIndexOutOfBoundsException();
        }

        // get the stock and return it
        String s = (String)stocks.elementAt(index);
        return s;
    }

    // set an individual element of the Stocks property array
    public synchronized void setStocks(int index, String stock)
            throws ArrayIndexOutOfBoundsException
    {
        // make sure the index is in bounds
        if (index < 0 || index >= getStockCount())
        {
            throw new ArrayIndexOutOfBoundsException();
        }

        // change the stock at the specified index
        stocks.setElementAt(stock, index);
    }
}
