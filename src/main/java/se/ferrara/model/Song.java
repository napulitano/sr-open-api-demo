package se.ferrara.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Slf4j
public class Song implements Comparable<Song>{
    @ToString.Include
    private String title;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String artist;
    @ToString.Include
    private String albumname;
    @EqualsAndHashCode.Include
    private String recordlabel;

    @Override
    public int compareTo(Song o) {
        return this.getArtist().compareTo(o.getArtist());
    }

    public String getRecordlabel() {
        if(Strings.isEmpty(recordlabel)) {
            recordlabel = "Unknown";
        }
        return recordlabel;
    }
}
