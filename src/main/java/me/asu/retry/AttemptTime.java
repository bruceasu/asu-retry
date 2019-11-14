package me.asu.retry;

import java.util.Date;
import lombok.Data;

/**
 * 尝试执行的时候消耗时间
 */
@Data
public class AttemptTime
{
    private Date startTime;
    private Date endTime;
    private long costTimeInMills;
}
