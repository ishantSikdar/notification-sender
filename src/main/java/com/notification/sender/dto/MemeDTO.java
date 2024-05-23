package com.notification.sender.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class MemeDTO {
    private String postLink;
    private String subreddit;
    private String title;
    private String url;
    private boolean nsfw;
    private String author;
    private boolean spoiler;
    private int ups;
    private List<String> preview;
}
