package com.electric.util;

/**
 * @author sunk
 * @date 2024/04/23
 */
public class SnowflakeIdGenerator {
    private long       datacenterId;                                                        // 数据中心ID
    private long       machineId;                                                           // 机器ID
    private long       sequence           = 0L;                                             // 序列号
    private long       lastTimestamp      = -1L;                                            // 上一次时间戳

    private final long twepoch            = 1288834974657L;
    private final long datacenterIdBits   = 5L;
    private final long machineIdBits      = 5L;
    private final long maxDatacenterId    = -1L ^ (-1L << datacenterIdBits);
    private final long maxMachineId       = -1L ^ (-1L << machineIdBits);
    private final long sequenceBits       = 12L;

    private final long machineIdShift     = sequenceBits;
    private final long datacenterIdShift  = sequenceBits + machineIdBits;
    private final long timestampLeftShift = sequenceBits + machineIdBits + datacenterIdBits;
    private final long sequenceMask       = -1L ^ (-1L << sequenceBits);

    public SnowflakeIdGenerator(long datacenterId, long machineId) {
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than %d or less than 0");
        }
        if (machineId > maxMachineId || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than %d or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (machineId << machineIdShift) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public static void main(String[] args) {
        SnowflakeIdGenerator idWorker = new SnowflakeIdGenerator(1, 1);
        for (int i = 0; i < 100; i++) {

            System.out.println(idWorker.nextId());
        }
    }
}
