package com.janbask.training3;

import java.beans.*;

public class Main {

    public static void main(String[] args) {
        //A simple java bean
        Employee e=new Employee();//object is created

        e.setName("Steve");//setting value to the object

        System.out.println(e.getName());

        //Indexed property usage
        String[] stocks = {"Abc Stock", "Def Stock", "Ghi Stock"};
        IndexedProperty indexedProperty = new IndexedProperty();
        //You can set the stocks in bulk
        indexedProperty.setStocks(stocks);
        //Or you can insert a stock at a time
        indexedProperty.setStocks(1, "Jkl Stock");
        //You can get the stocks in bulk
        String[] indexedStocks = indexedProperty.getStocks();
        //Or you can get one stock at a time
        String lastStock = indexedProperty.getStocks(indexedProperty.getStockCount()-1); //We are retrieving the last stock item.
        //printing all stocks
        System.out.println("***********\nPrinting all stocks\n***********");
        for (String stock:indexedStocks) {
            System.out.format("\nStock Name: %s", stock);
        }
        //print the stock at the last position
        System.out.format("\n\nStock at last position\nStock Name: %s\n***********", lastStock);

        //ToDo: Extract a stock from the 3 position
        //ToDo: Append additional 4 stocks to the Indexed Property Object and enumerate all stocks in the output

        //Bound property usage and event handlers
        BoundProperty boundProperty = new BoundProperty();
        boundProperty.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //Notice the string intrapolation (Format)
                System.out.format("\nProperty Changed on Bound Property object: %s \n[\n\tOld Value: %s\n\tNew Value: %s\n]", evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
            }
        });
        boundProperty.setMouthWidth(23);
        boundProperty.setMouthWidth(45); //increase the size and the property change event would print the values

        //Constrained properties and event handlers
        ConstrainedProperty constrainedProperty = new ConstrainedProperty();
        //Add a simple property change listener
        constrainedProperty.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //Notice the string intrapolation (Format)
                System.out.format("\nProperty Changed on Constrained Property object: %s \n[\n\tOld Value: %s\n\tNew Value: %s\n]", evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
            }
        });
        try {
            constrainedProperty.setMouthWidth(22);

            //Now lets add a vetoed property change listener
            constrainedProperty.addVetoableChangeListener(new VetoableChangeListener() {
                @Override
                public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
                    //Use cating when you are 100% sure that the value is going to be the target type - else try to use the parsing  both will
                    //throw an exception if the conversion fails
                    int oldValue = (int)evt.getOldValue(); //Casting
                    int newValue = Integer.parseInt(evt.getNewValue().toString()); //Parsing
                    //Now check if the change is large - let's say more than 50 - veto it if it is
                    int difference = Math.abs(newValue - oldValue);
                    if(difference>50) {
                        System.out.format("\nVetoing the change because it is larger than 50:\nRequested Change: %s", difference);
                        throw new PropertyVetoException("Change larger than 50 is not allowed", evt);
                    }
                    else{
                        System.out.format("\nProperty Changed Allowed on Constrained Property object: %s \n[\n\tOld Value: %s\n\tNew Value: %s\n\tDifference:%s\n]", evt.getPropertyName(), evt.getOldValue(), evt.getNewValue(), difference);
                    }
                }
            });
            //This change will go through - notice that the vetoable change event is fired first
            constrainedProperty.setMouthWidth(72);
            //This change will be vetoed and rolled back
            constrainedProperty.setMouthWidth(21);

        }
        catch (PropertyVetoException vetoException){
            PropertyChangeEvent event = vetoException.getPropertyChangeEvent();
            System.out.format("\nSome body vetoed the change from: %s to: %s", event.getOldValue(),event.getNewValue());
        }
        //Inspect the value it should still be 72
        int value = constrainedProperty.getMouthWidth();
        System.out.format("\n\n\nBecause the value wouldn't change due to veto the actual and expected values should be same\nThe value is: %s\nExpected value is: %s",
                value,72);
    }
}
