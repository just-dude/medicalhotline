package domain.common.util;

import dataAccess.common.Node;
import domain.common.HavingLongIdAccountableDomainObject;
import domain.common.HavingLongIdDomainObject;
import domain.common.HavingNameAndLongIdAccountableDomainObject;
import domain.common.HavingNameAndLongIdDomainObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DomainObjectUtils {

    public static <T extends HavingLongIdDomainObject> boolean isTwoSetsHasEqualDomainObjectsIds(Set<T> set1,Set<T> set2){
        return isTwoListsHasEqualDomainObjectsIds(setToList(set1),setToList(set2));
    }

    public static <T extends HavingLongIdDomainObject> boolean isTwoListsHasEqualDomainObjectsIds(List<T> list1,List<T> list2){
        List<Long> idsList1=list1.stream().map(o->o.getId()).sorted((v1, v2) -> Long.compare(v1, v2)).collect(Collectors.toList());
        List<Long> idsList2=list2.stream().map(o->o.getId()).sorted((v1, v2) -> Long.compare(v1, v2)).collect(Collectors.toList());
        return idsList1.equals(idsList2);
    }

    public static <T extends HavingLongIdAccountableDomainObject> boolean isTwoSetsHasEqualAccountableDomainObjectsIds(Set<T> set1,Set<T> set2){
        return isTwoListsHasEqualAccountableDomainObjectsIds(setToList(set1),setToList(set2));
    }

    public static <T extends HavingLongIdAccountableDomainObject> boolean isTwoListsHasEqualAccountableDomainObjectsIds(List<T> list1,List<T> list2){
        List<Long> idsList1=list1.stream().map(o->o.getId()).sorted((v1, v2) -> Long.compare(v1, v2)).collect(Collectors.toList());
        List<Long> idsList2=list2.stream().map(o->o.getId()).sorted((v1, v2) -> Long.compare(v1, v2)).collect(Collectors.toList());
        return idsList1.equals(idsList2);
    }

    public static <T extends HavingNameAndLongIdDomainObject> String domainObjectsSetToNamesString(Set<T> set){
        return domainObjectsListToNamesString(setToList(set));
    }

    public static <T extends HavingNameAndLongIdDomainObject> String domainObjectsListToNamesString(List<T> list){
        return list.stream().map(it->it.getName()).collect(Collectors.joining(", "));
    }

    public static <T extends HavingNameAndLongIdAccountableDomainObject> String accountableDomainObjectsSetToNamesString(Set<T> set){
        return accountableDomainObjectsListToNamesString(setToList(set));
    }

    public static <T extends HavingNameAndLongIdAccountableDomainObject> String accountableDomainObjectsListToNamesString(List<T> list){
        return list.stream().map(it->it.getName()).collect(Collectors.joining(", "));
    }

    public static <T> boolean isNodesListContainsData(List<Node<T>> nodeList,T data){
        for(Node<T> node:nodeList){
            if(node.containsData(data)){
                return true;
            }
        }
        return false;
    }

    public static <T> List<T> setToList(Set<T> set){
        List result =new ArrayList(set.size());
        result.addAll(set);
        return result;
    }


}
