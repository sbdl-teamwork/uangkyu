package uangkyu;

import java.util.ArrayList;

public class ActivityList {
  public ArrayList<Activity> activities = new ArrayList<Activity>();

  public ActivityList insert(Activity activity) {
    int activitiesLength = this.activities.size();

    if (activitiesLength != 0) {
      Activity lastActivity = this.activities.get(this.activities.size() - 1);
      activity.setId(lastActivity.getId() + 1);
    } else {
      activity.setId(0);
    }

    this.activities.add(activity);
    System.out.println();
    System.out.println("Aktifitas " + activity.getDescription() + " berhasil ditambahkan");
    return this;
  }
  
  public int[] getTotalNominal() {
      int totalIncome = 0;
      int totalOutcome = 0;
      
      for(Activity activity : this.activities) {
          if (activity.getType() == 1) totalIncome = totalIncome + activity.getNominal();
          else totalOutcome = totalOutcome + activity.getNominal();
      }
      
      int total[] = {totalOutcome, totalIncome};
      
      return total;
  }

  public int getIndexFromId(int id) {
    if (this.activities.size() < 1)
      return -1;

    int index = -1;
    boolean isFound = false;
    for (Activity activity : activities) {
      index++;

      if (activity.getId() == id) {
        isFound = true;
        break;
      }
    }
    index = isFound ? index : -1;

    return index;
  }

  public ActivityList getAll() {
    for (Activity activity : activities) {
      System.out.println(
          activity.getId() + "\t" + activity.getDescription() + "\t" + activity.getNominal() + "\t" + activity.getTypeAsText());
    }
    return this;
  }

  public ActivityList get(int id) {
    System.out.println();
    int index = this.getIndexFromId(id);

    if (index < 0) {
      System.out.println("Id : " + id + " tidak ditemukan");
    } else {
      Activity activity = this.activities.get(index);
      System.out.println(
          activity.getId() + "\t" + activity.getDescription() + "\t" + activity.getNominal() + "\t" + activity.getTypeAsText());
    }
    return this;
  }

  public ActivityList delete(int id) {
    int index = this.getIndexFromId(id);
    if (index < 0) {
      System.out.println("Id : " + id + " tidak ditemukan");
    } else {
      this.activities.remove(index);
      System.out.println("Id : " + id + " berhasil dihapus !");
    }
    return this;
  }

  public ActivityList update(int id, Activity activity) {
    int index = this.getIndexFromId(id);
    if (index < 0) {
      System.out.println("Id : " + id + " tidak ditemukan");
    } else {
      activity.setId(id);
      this.activities.set(index, activity);
      System.out.println("Id : " + id + " berhasil diperbarui");
    }
    return this;
  }
}
