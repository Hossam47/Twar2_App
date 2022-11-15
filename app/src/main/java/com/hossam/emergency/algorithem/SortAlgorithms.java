package com.hossam.emergency.algorithem;


import android.location.Location;

import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.ui.comments.CommentModel;
import com.hossam.emergency.ui.messanger.MessageModel;
import com.hossam.emergency.models.ImageModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by hossam on 3/1/18.
 */

public class SortAlgorithms {


    public ArrayList<CaseModel> getMaxTime(ArrayList<CaseModel> list) {

        Collections.sort(list, new Comparator<CaseModel>() {
            public int compare(CaseModel o1, CaseModel o2) {

                return o1.getTime() < o2.getTime() ? -1 : o1.getTime() > o2.getTime() ? 1 : doSecodaryOrderSort(o1, o2);
            }

            public int doSecodaryOrderSort(CaseModel o1, CaseModel o2) {
                return o1.getTime() < o2.getTime() ? -1 : o1.getTime() > o2.getTime() ? 1 : 0;
            }
        });

        Collections.reverse(list);

        return list;
    }

    public ArrayList<MessageModel> sortMessagesTime(ArrayList<MessageModel> list) {

        Collections.sort(list, new Comparator<MessageModel>() {
            public int compare(MessageModel o1, MessageModel o2) {

                return o1.getTime() < o2.getTime() ? -1 : o1.getTime() > o2.getTime() ? 1 : doSecodaryOrderSort(o1, o2);
            }

            public int doSecodaryOrderSort(MessageModel o1, MessageModel o2) {
                return o1.getTime() < o2.getTime() ? -1 : o1.getTime() > o2.getTime() ? 1 : 0;
            }
        });

        Collections.reverse(list);

        return list;
    }


    public ArrayList<CaseModel> getMaxRate(ArrayList<CaseModel> list) {

        Collections.sort(list, new Comparator<CaseModel>() {
            public int compare(CaseModel o1, CaseModel o2) {

                return o1.getCount() < o2.getCount() ? -1 : o1.getCount() > o2.getCount() ? 1 : doSecodaryOrderSort(o1, o2);
            }

            public int doSecodaryOrderSort(CaseModel o1, CaseModel o2) {
                return o1.getCount() < o2.getCount() ? -1 : o1.getCount() > o2.getCount() ? 1 : 0;
            }
        });

        Collections.reverse(list);

        return list;
    }

    public ArrayList<ImageModel> sortImage(ArrayList<ImageModel> list) {

        Collections.sort(list, new Comparator<ImageModel>() {
            public int compare(ImageModel o1, ImageModel o2) {

                return o1.getPosition() < o2.getPosition() ? -1 : o1.getPosition() > o2.getPosition() ? 1 : doSecodaryOrderSort(o1, o2);
            }

            public int doSecodaryOrderSort(ImageModel o1, ImageModel o2) {
                return o1.getPosition() < o2.getPosition() ? -1 : o1.getPosition() > o2.getPosition() ? 1 : 0;
            }
        });

        return list;
    }

    public ArrayList<CommentModel> sortComments(ArrayList<CommentModel> list) {

        Collections.sort(list, new Comparator<CommentModel>() {
            public int compare(CommentModel o1, CommentModel o2) {

                return o1.getTimestamp() < o2.getTimestamp() ? -1 : o1.getTimestamp() > o2.getTimestamp() ? 1 : doSecodaryOrderSort(o1, o2);
            }

            public int doSecodaryOrderSort(CommentModel o1, CommentModel o2) {
                return o1.getTimestamp() < o2.getTimestamp() ? -1 : o1.getTimestamp() > o2.getTimestamp() ? 1 : 0;
            }
        });

        Collections.reverse(list);

        return list;
    }

//    public ArrayList<ParkingModel> getMinPrice(ArrayList<ParkingModel> list) {
//
//        Collections.sort(list, new Comparator<ParkingModel>() {
//            public int compare(ParkingModel o1, ParkingModel o2) {
//
//                return o1.getPrice_hour() < o2.getPrice_hour() ? -1 : o1.getPrice_hour() > o2.getPrice_hour() ? 1 : doSecodaryOrderSort(o1, o2);
//            }
//
//            public int doSecodaryOrderSort(ParkingModel o1, ParkingModel o2) {
//                return o1.getPrice_hour() < o2.getPrice_hour() ? -1 : o1.getPrice_hour() > o2.getPrice_hour() ? 1 : 0;
//            }
//        });
//
//        return list;
//    }
//
public ArrayList<CaseModel> getNearDistance(ArrayList<CaseModel> list, final Location location) {

    Collections.sort(list, new Comparator<CaseModel>() {
        public int compare(CaseModel o1, CaseModel o2) {
            //Sorts by 'TimeStarted' property
            Location parkLocation1 = new Location("");

            parkLocation1.setLatitude(o1.getLatitude());
            parkLocation1.setLongitude(o1.getLongitude());

            Location parkLocation2 = new Location("");

            parkLocation2.setLatitude(o2.getLatitude());
            parkLocation2.setLongitude(o2.getLongitude());

            return location.distanceTo(parkLocation1) < location.distanceTo(parkLocation2) ? -1 : location.distanceTo(parkLocation1) >
                    location.distanceTo(parkLocation2) ? 1 : doSecodaryOrderSort(o1, o2);
        }

        //If 'TimeStarted' property is equal sorts by 'TimeEnded' property
        public int doSecodaryOrderSort(CaseModel o1, CaseModel o2) {
            return o1.getTime() < o2.getTime() ? -1 : o1.getTime() > o2.getTime() ? 1 : 0;
        }
    });

    return list;
}
}
