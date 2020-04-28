package se.ferrara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.ferrara.model.Channel;
import se.ferrara.model.RecordLabel;
import se.ferrara.model.Song;
import se.ferrara.service.SRService;

import java.util.List;

@Controller
public class IndexController {

    private SRService srService;

    @Autowired
    public IndexController(SRService srService) {
        this.srService = srService;
    }

    @GetMapping("/")
    public String index(@RequestParam(name = "channelId", required = false, defaultValue = "164") String channelId, @RequestParam(name = "start", required = false, defaultValue = "2020-04-27") String start, @RequestParam(name = "end", required = false, defaultValue = "2020-04-28") String end, Model model)  {
        model.addAttribute("channelId", channelId);

        List<Channel> channels = srService.fetchChannels();
        List<Song> songs = srService.fetchSongs(channelId,start,end);
        List<RecordLabel> labels = srService.getSongByRecordLabel(songs);
        model.addAttribute("channels", channels);
        model.addAttribute("playList", labels);
        model.addAttribute("channelName", channelId);

        return "index";
    }

    @GetMapping("/playlist")
    public ResponseEntity<List<RecordLabel>> recordLabels(@RequestParam(name = "channelId", required = false, defaultValue = "164") String channelId, @RequestParam(name = "start", required = false, defaultValue = "2020-04-27") String start, @RequestParam(name = "end", required = false, defaultValue = "2020-04-28") String end, Model model)  {

        List<Song> songs = srService.fetchSongs(channelId,start,end);
        List<RecordLabel> labels = srService.getSongByRecordLabel(songs);

        return ResponseEntity.ok(labels);
    }
    @GetMapping("/playlist/original")
    public ResponseEntity<List<Song>> playlist(@RequestParam(name = "channelId", required = false, defaultValue = "164") String channelId, @RequestParam(name = "start", required = false, defaultValue = "2020-04-27") String start, @RequestParam(name = "end", required = false, defaultValue = "2020-04-28") String end, Model model)  {

        List<Song> songs = srService.fetchSongs(channelId,start,end);
        return ResponseEntity.ok(songs);
    }

}