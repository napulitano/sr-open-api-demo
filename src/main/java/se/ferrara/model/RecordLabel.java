package se.ferrara.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class RecordLabel implements Comparable<RecordLabel>{
    @EqualsAndHashCode.Include
    private String name;
    private List<Song> songs;

    public void addSong(Song song){
        if(Objects.isNull(songs)) {
            songs = new ArrayList<>();
        }
        if(!songs.contains(song)) {
            songs.add(song);
        }
    }

    @Override
    public int compareTo(RecordLabel o) {
        return this.name.compareTo(o.name);
    }

    public String getName() {
        if(Strings.isEmpty(name)) {
            name = "Unknown";
        }
        return name;
    }
}
