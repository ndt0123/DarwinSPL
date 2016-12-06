
package eu.hyvar.reconfigurator.input.rest.dummyclient.hyvarrec_config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeatureModel {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("filename")
    @Expose
    private String filename;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("specification")
    @Expose
    private String specification;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * 
     * (Required)
     * 
     * @param filename
     *     The filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The specification
     */
    public String getSpecification() {
        return specification;
    }

    /**
     * 
     * (Required)
     * 
     * @param specification
     *     The specification
     */
    public void setSpecification(String specification) {
        this.specification = specification;
    }

}