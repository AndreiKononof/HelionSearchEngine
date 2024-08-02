package searchengine.services;


import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.RecursiveTask;

@AllArgsConstructor
public class RecursiveTaskMapSait extends RecursiveTask<HashSet<String>> {
    LinksSait mapSait;
    HashSet<String> checkLinks;

    @Override
    protected HashSet<String> compute() {
        List<RecursiveTaskMapSait> taskList = new ArrayList<>();
        HashSet<String> links = new HashSet<>(mapSait.getLinks());
        if (!links.isEmpty()) {
            for (String link : links) {
                if (!checkLinks.contains(link)) {
                    RecursiveTaskMapSait task = new RecursiveTaskMapSait(new LinksSait(mapSait.getUrl() + link), checkLinks);
                    task.fork();
                    taskList.add(task);
                    checkLinks.add(link);
                }
            }
            for (RecursiveTaskMapSait task : taskList) {
                links.addAll(task.join());
            }
        }
        return links;
    }
}
