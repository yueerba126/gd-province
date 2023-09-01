package com.sydata.common.tree;

import com.sydata.common.api.enums.YesNo;
import com.sydata.common.domain.KuNode;
import com.sydata.framework.util.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author zhoucl
 */
public class TreeGenerateUtils {
    private static final String COUNT_MODEL_COUNT = "COUNT";
    private static final String COUNT_MODEL_SUM = "SUM";

    public static List<KuNode> generate(Collection<KuNode> nodes) {
        return generate(nodes, false);
    }

    /**
     * 列表节点转换为树形节点
     */
    public static List<KuNode> generate(Collection<KuNode> nodes, boolean noParentAppendTop) {
        List<KuNode> newNodes = new ArrayList<>();
        if (CollectionUtils.isEmpty(nodes)) {
            return newNodes;
        }
        Map<Object, KuNode> newNodeMap = new HashMap<>(16);
        nodes.forEach(node -> {
            if (Objects.equals(YesNo.NO.getCode(), node.getParentKey())) {
                node.setParentKey("");
            }
            newNodeMap.put(node.getKey(), node);
            if (StringUtils.isBlank(node.getParentKey())) {
                newNodes.add(node);
            }
        });
        for (KuNode node : nodes) {
            if (StringUtils.isNoneBlank(node.getParentKey())) {
                KuNode parentKuNode = newNodeMap.get(node.getParentKey());
                if (parentKuNode != null) {
                    parentKuNode.getChildren().add(node);
                }
                if (parentKuNode == null && noParentAppendTop) {
                    newNodes.add(node);
                }
            }
        }
        newNodeMap.clear();
        return newNodes;
    }

    public static void count(List<KuNode> nodes) {
        count(nodes, COUNT_MODEL_COUNT);
    }

    public static void sum(List<KuNode> nodes) {
        count(nodes, COUNT_MODEL_SUM);
    }

    /**
     * COUNT_MODEL_COUNT：统计最低级叶子节点数量
     * COUNT_MODEL_SUM：节点本身有数量字段，累加数量字段
     */
    private static void count(List<KuNode> nodes, String countModel) {
        for (KuNode node : nodes) {
            if (COUNT_MODEL_COUNT.equals(countModel)) {
                doCount(node);
            } else if (COUNT_MODEL_SUM.equals(countModel)) {
                doSum(node);
            }
        }
    }

    /**
     * 树形结构展示的某个节点的统计数值=孩子总数+孩子的孩子总数
     */
    private static int doCount(KuNode root) {
        List<KuNode> list = root.getChildren();
        if (list == null || list.size() == 0) {
            return 0;
        }
        int count = 0;
        for (KuNode child : list) {
            if (CollectionUtils.isEmpty(child.getChildren())) {
                count++;
                child.setCnt(0);
            } else {
                //统计子节点的孩子总数
                int curCnt = doCount(child);
                child.setCnt(curCnt);
                count += curCnt;
            }
        }
        //返回前记录当前节点的统计个数
        root.setCnt(count);
        return count;
    }

    private static int doSum(KuNode root) {
        List<KuNode> list = root.getChildren();
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        int totalCount = 0;
        for (KuNode child : list) {
            int count = 0;
            count += child.getCnt();
            if (!CollectionUtils.isEmpty(child.getChildren())) {
                //统计子节点的孩子总数
                int curCnt = doSum(child);
                count += curCnt;
            }
            child.setCnt(count);
            totalCount += count;
        }
        //返回前记录当前节点的统计个数
        root.setCnt(totalCount + root.getCnt());
        return totalCount;
    }

}
