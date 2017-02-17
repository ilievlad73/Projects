/**
 *
 * @author Ilie
 */
public class Tree implements TreePart {

    private Node root;

    public Tree(Node root) {
        this.root = root;
    }

    /**functie auxiliara cu care se parcurge arborele
     *
     * @param root
     * @param treeParVisitor
     */
    public static void postOrder(Node root, TreePartVisitor treeParVisitor) {
        if (root != null) {

            postOrder(root.left, treeParVisitor);
            postOrder(root.right, treeParVisitor);
            root.accept(treeParVisitor);
        }

    }

    @Override
    public void accept(TreePartVisitor treeParVisitor) {
        postOrder(root, treeParVisitor);
    }
}
