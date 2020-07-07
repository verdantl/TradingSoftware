# Trading Program
CSC207 Project 

Amir Azimi, Jeffrey Huang, Arjun Pandher, Junhee Jung, Qinyu Wu, Carl Wu, Jinesh Patel, Yilong You

Notes:
- The configuration file runs with the Phase_1 folder as the base project. Using the group_0011 folder or the phase1 
folder will require a different file path name.
- For the Trader class, proposedItems = items awaiting approval, wantToLend = lending list, and wantToBorrow = wishlist
- We have some pre-made users and items already in the configuration file. To
create a new user, you can sign up for an account and specify if you'd like to submit an admin request
or just be a normal trader. To login, use the login option at the main screen.
- We assume that trade can be confirmed only after the day that the trade takes place. So if you want to test that functionality, please wait until the trade date is passed or change the trade date in configuration file maually after both users agree to the meeting.
- We don't explicitly state that a trader can borrow or lend. Because when A borrows an item from B, B inversely lends the item to A. It doesn't make sense to say this trade is simply lending or borrowing. Instead, we use "onewaytrade" to state that, where the initiator is someone who borrows the item while the receiver is someone who lends the item. As for the "the first trading must be lending or exchange", we mean the first trade can be either twoway or receiving an onewaytrade request from the initiator
- The menu of editing/agreeing/confirming the meeting shows up after you select a trade when you browse on-going trade
- A user is flagged iff the user trades over the the max number of incomplete trades. The user will be unflagged after the admin freeze/unfreeze the user's account