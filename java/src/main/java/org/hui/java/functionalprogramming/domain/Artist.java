package org.hui.java.functionalprogramming.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 创作音乐的人或团队.
 *
 * @author Hui.Liu
 */
@Setter
@Getter
@ToString
public class Artist {
    private String name;
    private List<String> members;
    private String origin;
}
