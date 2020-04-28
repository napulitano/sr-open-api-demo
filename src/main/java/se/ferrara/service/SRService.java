package se.ferrara.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.ferrara.model.Channel;
import se.ferrara.model.ChannelList;
import se.ferrara.model.RecordLabel;
import se.ferrara.model.Song;
import se.ferrara.model.SongList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SRService {


    @Value("${sr.api.endpoint.channels}")
    private String endpointChannels;
    @Value("${sr.api.endpoint.playlists}")
    private String endpointPlaylists;

    private RestTemplate restTemplate = new RestTemplate();


    public List<Channel> fetchChannels() {
        ChannelList channels = restTemplate.getForObject(endpointChannels, ChannelList.class);
        return channels.getChannels();
    }

    public List<Song> fetchSongs(String channelId, String start, String end) {
        SongList songList = restTemplate.getForObject(String.format(endpointPlaylists,channelId,start,end),SongList.class);
        List<Song> list = songList.getSong();
        Collections.sort(list);
        return list;
    }

    public List<RecordLabel> getSongByRecordLabel(List<Song> songs) {
        List<RecordLabel> recordLabels = new ArrayList<>();
        songs.forEach(song->{
            RecordLabel recordLabel = RecordLabel.builder()
                    .name(song.getRecordlabel())
                    .build();
            if(recordLabels.contains(recordLabel)) {
                int pos = recordLabels.indexOf(recordLabel);
                recordLabels.get(pos).addSong(song);
            } else {
                recordLabel.addSong(song);
                recordLabels.add(recordLabel);
            }

        });
        Collections.sort(recordLabels);
        return recordLabels;
    }
}
