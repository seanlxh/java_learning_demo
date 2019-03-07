public class SegmentTreeNode {
    public int start, end, max;
    public SegmentTreeNode left ,right;
    SegmentTreeNode(int start , int end, int max){
        this.start = start;
        this.end = end;
        this.max = max;
        this.left = this.right = null;
    }
    public SegmentTreeNode build(int[] A){
        return buildhelper(0,A.length-1,A);
    }

    public SegmentTreeNode buildhelper(int left, int right, int[] A){
        if(left > right){
            return null;
        }
        SegmentTreeNode treeNode = new SegmentTreeNode(left,right,A[left]);
        if(left == right)
            return treeNode;
        int mid = (left + right) / 2;

        treeNode.left = buildhelper(left,mid,A);
        treeNode.right = buildhelper(mid+1,right,A);
        treeNode.max = Math.max(treeNode.left.max, treeNode.right.max); // 根据节点区间的左右区间的节点值得到当前节点的节点值
        return treeNode;

    }

}

