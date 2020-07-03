import java.util.List;
import java.io.*;

public class ConfigWriter {

    public void saveFile(String fileName, TraderActions ta, AdminActions aa){
        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter(fileName));
            List<Trader> traders = ta.getTraders();
            List<Admin> admins = aa.getAdmins();
            List<Admin> reqAdmins = aa.getListOfRequestedAdmins();

            out.write(formatListOfTraders(traders));
            out.newLine();
            out.write("Wishlists:");
            out.newLine();
            out.write(formatListOfItems(traders));
            out.newLine();
            out.write("Trades:");
            out.newLine();
            out.write(formatListOfTrades(traders));
            out.newLine();
            out.write(formatAdmins(admins, reqAdmins));

            out.close();



        }catch (IOException iox){
            System.out.print("File Not Found");
        }
        //username,password,creationDate,isFrozen,isFlagged,requestedtounfreeze,numlent,numborrowed
        //WantToLend:
        //itemName,category,description,rating,itemID /wantToLend
        //ProposedItems:
        //itemName,category,description,rating,itemID /proposed
        //Trader:
        //username,password,creationDate,isFrozen,isFlagged,requestedtounfreezenumlent,numborrowed
        //WantToLend:
        //itemName,category,description,rating,itemID /wantToLend
        //ProposedItems:
        //itemName,category,description,rating,itemID /proposed
        //end
        //Wishlists:
        //Users who's wishlist we want to add items to, item1's ID, item1's owner's username, item2's id, item2's user's username, ....
        //end
        //BorrowedItems:
        //User's username who's borrowedItems we're adding to,Item name,Category,Description,rating,itemID,owner's username
        //end
        //Trades:
        //TradeType,initator's username,receiver's username,location,the date the trade will occur,isPermanent,isCompleted,returnDate(note that if a trade is permanent the date here is recorded as 0000-00-00),Initiator's username,isConfirmed(for initiator),numberOfEdits(for initiator), isAgreed(for initiator),receiver's username,isConfirmed(for reciever),numberOfEdits(for receiver),isAgreed(for receiver),TradeStatus.
        //end
        //admin,password,initiatedDate
        //end
        //admin2,1234
        //end

    }

    private String formatAdmins(List<Admin> admins, List<Admin> reqAdmins){
        String s = "";

        for (Admin a: admins){
            s += formatAdmin(a) + "\n";
        }
        s += "end\n";

        for (Admin a: reqAdmins){
            s += formatRequestedAdmin(a)+"\n";
        }
        s += "end";

        return s;
    }

    private String formatTrades(List<Trade> trades){
        String s = "";
        for (Trade t: trades){
            s += formatTrade(t)+"\n";
        }
        return s;
    }

    private String formatItems(List<Item> items){
        String s = "";
        for(Item i: items){
            s += formatItem(i)+"\n";
        }
        return s;
    }

    private String formatItem(Item i){
        return i.getName()+","+i.getCategory()+","+i.getDescription()+","+i.getQualityRating()+","+i.getId();
    }

    private String formatTrade(Trade t){
        String s = "";
        if(t instanceof OneWayTrade){
            s += "OneWayTrade"+",";
        }else{
            s += "TwoWayTrade"+",";
        }
        //TradeType,initator's username,receiver's username,location,the date the trade will occur,isPermanent,isCompleted,returnDate(note that if a trade is permanent the date here is recorded as 0000-00-00),Initiator's username,isConfirmed(for initiator),numberOfEdits(for initiator), isAgreed(for initiator),receiver's username,isConfirmed(for reciever),numberOfEdits(for receiver),isAgreed(for receiver),TradeStatus.
        s += t.getInitiator().getUsername()+","+t.getReceiver().getUsername()+","+t.getLocation()+","+t.getTradeDate().toString();
        //TODO We need to add something that will catch if the return date is null or not
        s += ","+t.isPermanent()+","+t.isCompleted()+","+t.getReturnDate().toString();
        s += ","+t.getInitiator().getUsername()+","+t.getIsConfirmed(t.getInitiator())+","+
                t.getNumberOfEdit(t.getInitiator())+","+t.getIsAgreed(t.getInitiator())+","+t.getReceiver().getUsername()+","+
                t.getIsConfirmed(t.getReceiver())+","+t.getNumberOfEdit(t.getReceiver())+
                ","+t.getIsAgreed(t.getReceiver())+","+t.getTradeStatus();
        return s;

    }

    private String formatTrader(Trader t){
        String s = t.getUsername()+","+t.getPassword()+","+t.getDateCreated()+","+t.isFrozen();
        s += ","+t.isFlagged()+","+t.isRequestToUnfreeze()+
                ","+t.getNumLent()+","+t.getNumBorrowed()+"\n";

        return s;

    }

    private String formatListOfTraders(List<Trader> traders){
        String s = "";
        if (traders.size() == 0){
            return s;
        }else if (traders.size() == 1){
            s += formatTrader(traders.get(0));
            s += "WantToLend:\n";
            s += formatItems(traders.get(0).getWantToLend());
            s += "ProposedItems:\n";
            s += formatItems(traders.get(0).getProposedItems());
            return s;
        }else{
            for (int i = 0;i < traders.size()-1;i++){
                Trader t = traders.get(i);
                s += formatTrader(t);
                s += "WantToLend:\n";
                s += formatItems(t.getWantToLend());
                s += "ProposedItems:\n";
                s += formatItems(t.getProposedItems());
                s += "Trader:\n";
            }
            s += formatTrader(traders.get(traders.size()-1));
            s += "WantToLend:\n";
            s += formatItems(traders.get(traders.size()-1).getWantToLend());
            s += "ProposedItems:\n";
            s += formatItems(traders.get(traders.size()-1).getProposedItems());
            s += "end";

            return s;
        }
    }

    private String formatListOfItems(List<Trader> traders){
        String s = "";
        for(Trader t: traders){
            s += formatWishlist(t.getWantToBorrow(),t.getUsername());
        }
        s += "end\n";

        for(Trader t: traders){
            s += formatBorrowedItems(t.getBorrowedItems(), t.getUsername());
        }
        s += "end";

        return s;
    }

    private String formatListOfTrades(List<Trader> traders){
        String s = "";
        for(Trader t: traders){
            s += formatTrades(t.getTrades());
        }
        s += "end";
        return s;
    }

    private String formatAdmin(Admin a){
        return a.getUsername()+","+a.getPassword()+","+a.getDateCreated();

    }

    private String formatRequestedAdmin(Admin a){
        return a.getUsername()+","+a.getPassword();
    }

    private String formatWishlist(List<Item> items, String name){
        String s = "";
        if (items.size() == 0){
            return s;
        }else if(items.size() == 1){
            return formatWishlistItem(items.get(0), name)+"\n";
        }else{
            for(int i = 0;i < items.size()-1;i++){
                s += formatWishlistItem(items.get(i), name)+",";
            }
        }
        s += formatWishlistItem(items.get(items.size()-1), name)+"\n";
        return s;
    }

    private String formatBorrowedItems(List<Item> items, String name){
        String s = "";
        if (items.size() == 0){
            return s;
        }else{
            for (Item i: items){
                s += formatBorrowedItem(i, name)+"\n";
            }
        }
        return s;
    }

    private String formatWishlistItem(Item i, String name){
        return name+","+i.getId()+","+i.getOwner().getUsername();
    }

    private String formatBorrowedItem(Item i, String name){
        String s = "";
        s += name+","+formatItem(i)+","+i.getOwner().getUsername();
        return s;
    }

}
