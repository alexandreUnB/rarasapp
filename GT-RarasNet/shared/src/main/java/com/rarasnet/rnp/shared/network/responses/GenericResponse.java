package com.rarasnet.rnp.shared.network.responses;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class GenericResponse implements Parcelable {

	@SerializedName("st") private String status; // status
	@SerializedName("dt" )private String data; // data
    @SerializedName("User")private String k;
	
	/*
	 * Parcelable Creator
	 */
	public static final Creator<GenericResponse> CREATOR = new Creator<GenericResponse>() {
		@Override
		public GenericResponse createFromParcel(Parcel source) {
			return new GenericResponse(source);
		}
		@Override
		public GenericResponse[] newArray(int size) {
			return new GenericResponse[size];
		}
	};
	
	public GenericResponse(Parcel source) {
		setStatus(source.readString());
		setData(source.readString());
        setK(source.readString());
        //Log.d("response",getData());
	}
	
	public GenericResponse() {}

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setData(String data) {
		this.data = data;
		Log.d("RESPONSE DATA:", data);
	}

	public String getData() {
		return this.data;
	}

	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(status);
		dest.writeString(data);
        dest.writeString(k);
	}

    @Override
    public String toString() {
        return "GenericResponse{" +
				"User'"+ k+'\''+
                "st='" + status + '\'' +
                ", data='" + data + '\'' +

                '}';
    }
}
