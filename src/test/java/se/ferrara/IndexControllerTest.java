package se.ferrara;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import se.ferrara.controller.IndexController;
import se.ferrara.model.Channel;
import se.ferrara.model.ChannelList;
import se.ferrara.model.RecordLabel;
import se.ferrara.model.Song;
import se.ferrara.model.SongList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private IndexController controller;

    private RestTemplate restTemplate = new RestTemplate();

    private String start = "2020-04-27";

    private String end = "2020-04-28";

    private String channelId = "164";



    @Test
    public void testIndex() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        List<Channel> channels = restTemplate.getForObject("http://api.sr.se/api/v2/channels?size=100&format=json", ChannelList.class).getChannels();;
        SongList songList = restTemplate.getForObject(String.format("http://api.sr.se/api/v2/playlists/getplaylistbychannelid?id=%s&startdatetime=%s&enddatetime=%s&format=json&size=100",channelId,start,end),SongList.class);
        List<Song> list = songList.getSong();
        Collections.sort(list);
        List<RecordLabel> playlist = getSongByRecordLabel(list);
        ResultActions resultActions = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("channels", is(channels)))
                .andExpect(model().attribute("playList", is(playlist)));

        MvcResult mvcResult = resultActions.andReturn();
        ModelAndView mv = mvcResult.getModelAndView();
        //
    }

    private List<RecordLabel> getSongByRecordLabel(List<Song> songs) {
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
