package org.hui.java.functionalprogramming.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 专辑.
 *
 * @author Hui.Liu
 */
@Setter
@Getter
@ToString
public class Album {
    private String name;
    private List<Track> tracks;
    private List<Artist> musicians;
}
