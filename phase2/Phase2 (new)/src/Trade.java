public class Trade {
    private String initiator;
    private String receiver;
    private boolean isPermanent;


    /**Getter for whether or not the trade is permanent
     * @return whether or not the trade is permanent
     */
    public boolean isPermanent() {
        return isPermanent;
    }

    /**Getter for whether or not the trade is permanent
     * @param permanent whether or not the trade is permanent
     */
    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }
}
