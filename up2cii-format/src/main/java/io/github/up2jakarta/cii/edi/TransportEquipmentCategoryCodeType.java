package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.TransportEquipmentCategoryCodeAdapter;
import io.github.up2jakarta.xml.codelist.Agency;
import io.github.up2jakarta.xml.codelist.CodeList;
import io.github.up2jakarta.xml.codelist.Documented;
import io.github.up2jakarta.xml.codelist.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 8053 : Equipment type code qualifier.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred8053.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Transport Equipment Category Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.5", date = "2008-08-23")
@XmlJavaTypeAdapter(TransportEquipmentCategoryCodeAdapter.class)
public enum TransportEquipmentCategoryCodeType implements CodeList<TransportEquipmentCategoryCodeType> {

    /**
     * Ground equipment being fuelled or serviced.
     */
    AA("AA", "Ground equipment"),

    /**
     * Chain used in the securing of cargo.
     */
    AB("AB", "Chain"),

    /**
     * Temperature recorder to provide a record of the actual temperature.
     */
    AD("AD", "Temperature recorder"),

    /**
     * The part of the vehicle where the cargo is loaded.
     */
    AE("AE", "Body trailer"),

    /**
     * A cardboard platform used for holding product for storage or transportation.
     */
    AG("AG", "Slipsheet"),

    /**
     * A code to indicate that there is no special equipment needed.
     */
    AH("AH", "No special equipment needed"),

    /**
     * A compartment forming part of a transport vessel.
     */
    AI("AI", "Vessel hold"),

    /**
     * Type of open container used for carrying objects.
     */
    AJ("AJ", "Flat rack"),

    /**
     * To indicate that the equipment is an aircraft.
     */
    AK("AK", "Aircraft"),

    /**
     * A device used for medical purposes.
     */
    AL("AL", "Medical device"),

    /**
     * A refrigerated (reefer) container that is actively cooling the product.
     */
    AM("AM", "Refrigerated container"),

    /**
     * A pallet with standard dimensions 80*120 centimetres made of synthetic material.
     */
    AN("AN", "Synthetic pallet 80*120cm"),

    /**
     * A standard pallet with standard dimensions 100*120 centimetres made of synthetic material.
     */
    AO("AO", "Synthetic pallet 100*120cm"),

    /**
     * Equipment used to store and transport clothing in a hanging position.
     */
    AP("AP", "Clothing hanger rack"),

    /**
     * Trailer designated for combined road/rail use.
     */
    AQ("AQ", "Road/rail trailer"),

    /**
     * Empty rail wagon added to the loaded wagons, when goods are longer than the loaded wagon.
     */
    AT("AT", "Overhang wagon"),

    /**
     * Cargoes in bulk not stuffed in equipment.
     */
    BB("BB", "Un-containerized cargo (breakbulk)"),

    /**
     * A piece of equipment that is normally a piece of wood to fix cargo (e.g. coils) during
     * transport.
     */
    BL("BL", "Blocks"),

    /**
     * A box pallet which cannot be exchanged.
     */
    BPN("BPN", "Box pallet non-exchangeable"),

    /**
     * A road vehicle capable of carrying goods which is being carried on another means of
     * transport.
     */
    BPO("BPO", "Truck being transported"),

    /**
     * A road vehicle capable of carrying goods with an attached trailer which is being carried
     * on another means of transport.
     */
    BPP("BPP", "Truck and trailer combination being transported"),

    /**
     * A trailer accompanied by a self-propelling tractor unit which is being carried on another
     * means of transport.
     */
    BPQ("BPQ", "Tractor and trailer being transported"),

    /**
     * Bag intended primarily for the conveyance of postal items.
     */
    BPR("BPR", "Postal bag"),

    /**
     * Tray intended primarily for the conveyance of letter mail items of size slightly exceeding
     * C5, in which the items are stood on their long edge.
     */
    BPS("BPS", "Letter Tray"),

    /**
     * Wheeled wire cage.
     */
    BPT("BPT", "Roller Cage"),

    /**
     * Tray intended primarily for the conveyance of letter mail items of from C5 up to in
     * excess of C4, in which the items are stacked one on top of another. May also be used
     * for letter mail smaller items stood on their long edge.
     */
    BPU("BPU", "Flats Tray"),

    /**
     * Postal item which is conveyed individually, with only the wrapping provided by the mailer
     * for protection.
     */
    BPV("BPV", "Out of bag parcel"),

    /**
     * Device, consisting of a flat surface mounted on a wheels, which is designed for the
     * conveyance of items which may safely be stacked. Normally also equipped with a handle
     * to allow the device to be easily manoeuvred.
     */
    BPW("BPW", "Wheeled Platform"),

    /**
     * The container is not compliant with the Customs Convention on Containers.
     */
    BPX("BPX", "Container non-compliant with the Customs Convention on Containers"),

    /**
     * A euro-pallet of type Y that may not be exchanged.
     */
    BPY("BPY", "Box pallet EUR Y non exchangeable"),

    /**
     * 80 X 60 cm pallet on casters.
     */
    BPZ("BPZ", "Roll Pallet 1"),

    /**
     * Flat bottomed inland cargo vessel for canals and rivers with or without own propulsion
     * for the purpose of transported goods. (Synonym: Lighter).
     */
    BR("BR", "Barge"),

    /**
     * An enclosed railway goods wagon.
     */
    BX("BX", "Boxcar"),

    /**
     * A wheeled carriage onto which an ocean container is mounted for inland conveyance.
     */
    CH("CH", "Chassis"),

    /**
     * Equipment item as defined by ISO for transport. It must be of: A) permanent character,
     * strong enough for repeated use; B) designed to facilitate the carriage of goods, by
     * one or more modes of transport, without intermediate reloading; C) fitted with devices
     * for its ready handling, particularly.
     */
    CN("CN", "Container"),

    /**
     * A set of panels fixed inside a porthole.
     */
    DPA("DPA", "Deadlight (panel)"),

    /**
     * 120 X 60 cm pallet on casters.
     */
    DPB("DPB", "Roll Pallet 2"),

    /**
     * A fixed gantry crane ashore for container handling operations.
     */
    DPC("DPC", "Container gantry crane"),

    /**
     * A mobile crane for handling operations.
     */
    DPD("DPD", "Mobile crane"),

    /**
     * A crane mounted on a dedicated vessel.
     */
    DPE("DPE", "Floating crane"),

    /**
     * A crane mounted on a ship for handling operations.
     */
    DPF("DPF", "Ship's equipment crane"),

    /**
     * Rolling carpet belt.
     */
    DPG("DPG", "Conveyor belt"),

    /**
     * Equipment device for handling and moving goods.
     */
    DPH("DPH", "Forklift"),

    /**
     * Equipment device for stacking goods.
     */
    DPI("DPI", "Stacking equipment"),

    /**
     * Equipment device at the rear of a truck for loading and unloading cargo.
     */
    DPJ("DPJ", "Taillift"),

    /**
     * Standard pallet with dimensions 80 X 60 cm.
     */
    DPK("DPK", "Pallet ISO 0 - 1/2 EURO Pallet"),

    /**
     * Equipment permanently on board a means of transport.
     */
    DPL("DPL", "On-board equipment"),

    /**
     * Standard pallet with dimensions 80 X 120 cm.
     */
    DPM("DPM", "Pallet ISO 1 - 1/1 EURO Pallet (GS1 Temporary Code)"),

    /**
     * Standard pallet with dimensions 100 X 120 cm.
     */
    DPN("DPN", "Pallet ISO 2"),

    /**
     * Standard pallet with dimensions 60 X 40 cm.
     */
    DPO("DPO", "1/4 EURO Pallet"),

    /**
     * A flat euro-pallet that may be exchanged.
     */
    EFP("EFP", "Exchangeable EUR flat pallet"),

    /**
     * Standard pallet with dimensions 40 X 30 cm.
     */
    EFQ("EFQ", "1/8 EURO Pallet"),

    /**
     * Pallet provided by the wholesaler.
     */
    EFR("EFR", "Wholesaler pallet"),

    /**
     * Pallet with dimensions 80 X 100 cm.
     */
    EFS("EFS", "Pallet 80 X 100 cm"),

    /**
     * Pallet with dimensions 60 X 100 cm.
     */
    EFT("EFT", "Pallet 60 X 100 cm"),

    /**
     * Pallet need not be returned to the point of expedition.
     */
    EFU("EFU", "Oneway pallet"),

    /**
     * Pallet must be returned to the point of expedition.
     */
    EFV("EFV", "Returnable pallet"),

    /**
     * A container for the storage or movement of bottles, a.k.a. bottlerack.
     */
    EFW("EFW", "Bottlecrate"),

    /**
     * A non-protected cylindrical container with a narrow neck made usually of glass or plastic
     * which is especially used for liquids.
     */
    EFX("EFX", "Bottle, non-protected, cylindrical"),

    /**
     * A lidded package which can be made of cardboard, wood, plastic,tin,etc.
     */
    EFY("EFY", "Box"),

    /**
     * A box mounted on a pallet base under the control of CHEP.
     */
    EFZ("EFZ", "CHEP Eurobox"),

    /**
     * A package such as a box.
     */
    EGA("EGA", "Case"),

    /**
     * A package used for the dispaly of goods, usually during a promotion.
     */
    EGB("EGB", "Display package"),

    /**
     * A case used for products which require constant temperature control.
     */
    EGC("EGC", "Isothermic case"),

    /**
     * Collars, with the dimensions 80cms * 100cms, which when fitted onto a pallet enable
     * the pallet to be transformed into a box pallet with, if necessary, a lid.
     */
    EGD("EGD", "Pallet modular collars 80*100"),

    /**
     * Collars, with the dimensions 80cms * 120cms, which when fitted onto a pallet enable
     * the pallet to be transformed into a box pallet with, if necessary, a lid.
     */
    EGE("EGE", "Pallet modular collars 80*120"),

    /**
     * A flat receptacle with low sides for carrying or holding articles.
     */
    EGF("EGF", "Tray"),

    /**
     * A three sided cage mounted on wheels.
     */
    EGG("EGG", "Roll cage"),

    /**
     * A low cart for the transportation and storage of groceries, milk, etc.
     */
    EGH("EGH", "Trolley"),

    /**
     * A generator located landside at a terminal, used to provide electric power to a vessel
     * or other means of transport.
     */
    EGI("EGI", "Landside power generator"),

    /**
     * A euro-pallet of type Y that may be exchanged.
     */
    EYP("EYP", "Exchangeable EUR Y box pallet"),

    /**
     * A flat euro-pallet that may not be exchanged.
     */
    FPN("FPN", "Flat pallet EUR non exchangeable"),

    /**
     * A non-exchangeable flat pallet owned by a railroad.
     */
    FPR("FPR", "Flat pallet (railway property) non-exchangeable"),

    /**
     * Lidded stackable rigid tray compliant with CEN TS 14482:2002.
     */
    IL("IL", "Lidded stackable rigid tray (CEN TS 14482:2002)"),

    /**
     * A rope for lashing cargo.
     */
    LAR("LAR", "Lashing rope"),

    /**
     * A mechanical device used in the loading and/or unloading of cargo into and from transport
     * equipment.
     */
    LU("LU", "Load/unload device on equipment"),

    /**
     * A panel which can be moved.
     */
    MPA("MPA", "Movable panel"),

    /**
     * A platform on which goods can be stacked in order to facilitate the movement by a forklift
     * or sling.
     */
    PA("PA", "Pallet"),

    /**
     * A box pallet identified as being privately owned.
     */
    PBP("PBP", "Identified private box pallet"),

    /**
     * A flat pallet identified as being privately owned.
     */
    PFP("PFP", "Identified private flat pallet"),

    /**
     * A piece of equipment normally having a flat surface, or prepared for carrying cargo
     * with a specific shape.
     */
    PL("PL", "Platform"),

    /**
     * A panel used for protection.
     */
    PPA("PPA", "Protecting panel"),

    /**
     * A portable heating unit.
     */
    PST("PST", "Portable stove"),

    /**
     * A railway wagon without raised sides or ends.
     */
    RF("RF", "Flat car"),

    /**
     * A generator used to control the temperature in temperature-controlled transport equipment.
     */
    RG("RG", "Reefer generator"),

    /**
     * Storage tank or facility capable of receiving shipment of goods or commodity.
     */
    RGF("RGF", "Ground facility"),

    /**
     * Rope used in the securing of cargo.
     */
    RO("RO", "Rope"),

    /**
     * To identify that the equipment is a rail car.
     */
    RR("RR", "Rail car"),

    /**
     * A road trailer without a front axle and with wheels only at the back end.
     */
    @Deprecated
    SM("SM", "Semi-trailer"),

    /**
     * A pallet identified as special.
     */
    SPP("SPP", "Identified special pallet"),

    /**
     * A narrow strip of flexible material.
     */
    STR("STR", "Strap"),

    /**
     * Rectangular equipment unit without wheels, which can be mounted on a chassis or positioned
     * on legs.
     */
    SW("SW", "Swap body"),

    /**
     * A vehicle without motive power, designed for the carriage of cargo and to be towed by
     * a motor vehicle.
     */
    TE("TE", "Trailer"),

    /**
     * A tank container used for the storage, transport and stockpiling of bulk cargoes such
     * as gases or liquids.
     */
    @Deprecated
    TN("TN", "Tank"),

    /**
     * Waterproof material, e.g. canvas, to spread over cargo to protect it from getting wet.
     */
    TP("TP", "Tarpaulin"),

    /**
     * Identification of loading tackle used (sheets, ropes, chains, etc.), as specified in
     * DCU 9 to CIM Article 13; and of containers, as specified in Articles 5 and 10 of Annex
     * III to CIM (CIM 17).
     */
    TS("TS", "Tackles"),

    /**
     * A device to support a tarpaulin.
     */
    TSU("TSU", "Tarpaulin support"),

    /**
     * An aircraft container or pallet.
     */
    UL("UL", "ULD (Unit load device)"),
    ;

    private final String name;
    private final String code;

    TransportEquipmentCategoryCodeType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }

}
