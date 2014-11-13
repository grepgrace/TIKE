package test;

import java.io.*;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;

import com.tike.model.geo.GEO_Data;
import com.tike.model.geo.MetaMap;
import com.tike.utils.Tools;

public class batchUpdateGeoMetaMap extends NutZTest {

	static long startTime = System.currentTimeMillis(); // 获取开始时间
	static long endTime = System.currentTimeMillis(); // 获取结束时间
	
	static Integer threadFinishedNum = -1;
	static int count = 0;
	static int threadNum = 40;
	static List<GEO_Data> list = null;

	public static void main(String[] args) throws IOException {
		batchUpdateGeoMetaMapMutliThread();
	}

	public static void batchUpdateGeoMetaMapMutliThread() {
		setDao();
		if (list == null) {// 6430
			Condition cd = Cnd.wrap("1=1");
			// cd = Cnd.wrap(" Id in (88, 360, 483, 806, 807, 817, 820)");
			list = basicDao.search(GEO_Data.class, cd);
			count = (int) Math.ceil((list.size() / (double) threadNum)); // 10/3=4
			System.out.println(" list.size:" + list.size() + " count:" + count);
		}
		if (threadNum > list.size())
			threadNum = list.size();
		for (int i = 0; i <= threadNum; i++) {
			new Thread(i + "") {
				public void run() {
					int index = Integer.valueOf(getName());
					System.err.println("thread " + index + "/" + threadNum + " start()");
					try {
						batchUpdateGeoMetaMap(index);
					} catch (IOException e) {
						System.err.println(index + "" + e.getMessage());
						e.printStackTrace();
					}
					synchronized (threadFinishedNum) {
						threadFinishedNum = threadFinishedNum + 1;
						if (threadFinishedNum.equals(threadNum)) {// all thread finished
							closeDao();
							endTime = System.currentTimeMillis(); // 获取结束时间
							System.out.println("程序运行时间： " + (endTime - startTime) + " ms");
						}
					}
					System.err.println("thread " + index + "/" + threadNum + " end()");
				}
			}.start();
		}
	}

	public static void batchUpdateGeoMetaMap(int index) throws IOException {
		int startIndex = count * (index);
		int endIndex = startIndex + count;
		if (endIndex >= list.size())
			endIndex = list.size();
		for (int i = startIndex; i < endIndex; i++) {
			GEO_Data geo = list.get(i);
			System.err.println("thread " + index + " " + (i + 1) + "/" + list.size() + " "
					+ geo.getId() + " " + startIndex + "-" + endIndex);
			String ids = ""; // like 'C1513882','C0007600'
			if (geo.getSummary() == null)
				continue;
			String text = geo.getSummary().toString();
			String filepath = Tools.dir + "tmp\\geo" + geo.getId() + ".txt";
			if (!text.endsWith("\r\n"))
				text = text + "\r\n";
			Tools.writeFile(filepath, text);
			List<MetaMap> metamaps = Tools.getMimeMaps(new File(filepath));
			for (int j = 0; j < metamaps.size(); j++) {
				MetaMap map = metamaps.get(j);
				map.setGEOId(geo.getId());
				basicDao.insert(map);
			}
		}
	}


}
