package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.TransportMeansTypeCodeAdapter;
import io.github.up2jakarta.xml.clv.Agency;
import io.github.up2jakarta.xml.clv.CodeList;
import io.github.up2jakarta.xml.clv.Documented;
import io.github.up2jakarta.xml.clv.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT R28 : Transport Means Type Code.
 * {@see https://unece.org/trade/uncefact/cl-recommendations}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Transport Means Type Code", agency = Agency.UN_ECE, version = "2007")
@Schema(agency = "UN/CEFACT", version = "4.0", date = "2008-08-23")
@XmlJavaTypeAdapter(TransportMeansTypeCodeAdapter.class)
public enum TransportMeansTypeCodeType implements CodeList<TransportMeansTypeCodeType> {

    /**
     * Vessel designed to carry grain.
     */
    V_1501("1501", "Grain vessel"),

    /**
     * Vessel designed to carry logs and timber.
     */
    V_1502("1502", "Timber/log carrier"),

    /**
     * Vessel designed to carry wood chips.
     */
    V_1503("1503", "Wood chips vessel"),

    /**
     * Vessel designed to carry steel products.
     */
    V_1504("1504", "Steel products vessel"),

    /**
     * Vessel designed to carry general cargo and containers.
     */
    V_1505("1505", "Carrier, general cargo/container"),

    /**
     * Vessel designed to carry temperature-controlled cargo.
     */
    V_1506("1506", "Temperature controlled cargo vessels"),

    /**
     * Vessel designed to carry containers only.
     */
    V_1511("1511", "Full container ship/cellular vessel"),

    /**
     * Vessel with ramp designed to carry roll-on/roll-off cargo.
     */
    V_1512("1512", "RoRo vessel"),

    /**
     * Vessel designed to carry automotive vehicles or their knock-down parts.
     */
    V_1513("1513", "Car carrier"),

    /**
     * Vessel designed to carry livestock.
     */
    V_1514("1514", "Livestock carrier"),

    /**
     * Vessel designed to carry barges. Lash means lighters aboard ship.
     */
    V_1515("1515", "Barge carrier - Lash ship"),

    /**
     * Vessel designed to carry chemicals in bulk or drums not in tanks.
     */
    V_1516("1516", "Chemical carrier"),

    /**
     * Vessel designed to carry irradiated fuel.
     */
    V_1517("1517", "Irradiated fuel carrier"),

    /**
     * Ship designed to carry heavy cargo.
     */
    V_1518("1518", "Heavy cargo vessel"),

    /**
     * Vessel designed to carry both containers and roll-on/roll-off cargo.
     */
    V_1519("1519", "RoRo/Container vessel"),

    /**
     * Vessel designed to carry dry bulk (expellers).
     */
    V_1521("1521", "Dry bulk carrier"),

    /**
     * Vessel designed to carry ore.
     */
    V_1522("1522", "Ore carrier"),

    /**
     * Vessel designed to carry cement.
     */
    V_1523("1523", "Cement carrier"),

    /**
     * Vessel designed to carry gravel.
     */
    V_1524("1524", "Gravel carrier"),

    /**
     * Vessel designed to carry coal.
     */
    V_1525("1525", "Coal carrier"),

    /**
     * Tanker designed to carry crude oil.
     */
    V_1531("1531", "Crude oil tanker"),

    /**
     * Tanker designed to carry chemicals in coastal traffic.
     */
    V_1532("1532", "Chemical tanker, coaster"),

    /**
     * Tanker designed to carry chemicals in deep sea.
     */
    V_1533("1533", "Chemical tanker, deep sea"),

    /**
     * Tanker designed to carry oil and other derivatives.
     */
    V_1534("1534", "Oil and other derivatives tanker"),

    /**
     * Vessel designed to carry Liquefied Petroleum Gas (LPG).
     */
    V_1541("1541", "LPG tanker"),

    /**
     * Tanker designed to carry Liquefied Natural Gas (LNG).
     */
    V_1542("1542", "LNG tanker"),

    /**
     * Tanker designed to carry Liquefied Natural Gas (LNG) and Liquefied Petroleum Gas (LPG).
     */
    V_1543("1543", "LNG/LPG tanker"),

    /**
     * Tanker designed asphalt and bitumen.
     */
    V_1551("1551", "Asphalt/bitumen tanker"),

    /**
     * Tanker designed to carry molasses.
     */
    V_1552("1552", "Molasses tanker"),

    /**
     * Tanker designed to carry vegetable oil.
     */
    V_1553("1553", "Vegetable oil tanker"),

    /**
     * Passenger ship designed to carry tourists on specified routes.
     */
    V_1591("1591", "Cruise ship"),

    /**
     * Vessel designed to ply regularly between two or more ports.
     */
    V_1592("1592", "Ferry"),

    /**
     * Vessel designed to carry passengers, not otherwise specified.
     */
    V_1593("1593", "Other passenger ship"),

    /**
     * Vessel designed to carry passengers and mainly propelled by sails.
     */
    V_1594("1594", "Passenger ship, sailing"),

    /**
     * Vessel designed to tow objects but sailing alone.
     */
    V_1601("1601", "Tug, without tow"),

    /**
     * Vessel designed to tow, and towing an object.
     */
    V_1602("1602", "Tug, with tow"),

    /**
     * Vessel designed to salvage.
     */
    V_1603("1603", "Salvage vessel"),

    /**
     * Vessel designed to effect rescue operations.
     */
    V_1604("1604", "Rescue vessel"),

    /**
     * Vessel designed to combat oil spills.
     */
    V_1605("1605", "Oil combat vessel"),

    /**
     * Object designed for drilling oil at sea.
     */
    V_1606("1606", "Oil rig"),

    /**
     * Vessel designed to serve as a hospital at sea.
     */
    V_1607("1607", "Hospital vessel"),

    /**
     * Vessel designed to convey pilots to/from ships.
     */
    V_1711("1711", "Pilot boat"),

    /**
     * Vessel designed to guard, patrol or measure.
     */
    V_1712("1712", "Patrol/measure ship"),

    /**
     * Vessel designed to provide supplies.
     */
    V_1721("1721", "Supply vessel"),

    /**
     * Vessel designed to provide offshore support.
     */
    V_1723("1723", "Offshore support vessel"),

    /**
     * Flat-bottomed vessel with a flat deck.
     */
    V_1724("1724", "Pontoon"),

    /**
     * Vessel designed to dump stones.
     */
    V_1725("1725", "Stone dumping vessel"),

    /**
     * Vessel designed to lay cable.
     */
    V_1726("1726", "Cable layer"),

    /**
     * Vessel designed to handle buoys.
     */
    V_1727("1727", "Buoyage vessel"),

    /**
     * Vessel designed to break ice.
     */
    V_1728("1728", "Icebreaker"),

    /**
     * Vessel designed to lay pipe.
     */
    V_1729("1729", "Pipelaying vessel"),

    /**
     * Vessel designed to drag a bag-like net.
     */
    V_1751("1751", "Trawler"),

    /**
     * Small vessel that sometimes can be carried on a larger ship.
     */
    V_1752("1752", "Cutter"),

    /**
     * Vessel designed as a fish factory.
     */
    V_1753("1753", "Factory ship"),

    /**
     * Vessel designed for fishery research.
     */
    V_1761("1761", "Fishery research vessel"),

    /**
     * Vessel designed for climate registration.
     */
    V_1762("1762", "Climate registration vessel"),

    /**
     * Vessel designed for environmental monitoring and measurement.
     */
    V_1763("1763", "Ship for environmental measurement"),

    /**
     * Vessel designed for scientific purposes.
     */
    V_1764("1764", "Scientific vessel"),

    /**
     * Vessel designed for training, powered by sail.
     */
    V_1765("1765", "Sailing school ship"),

    /**
     * Vessel designed for training.
     */
    V_1766("1766", "Training vessel"),

    /**
     * A crane mounted on a barge or pontoon.
     */
    V_1781("1781", "Crane, floating"),

    /**
     * A submersible floating structure used as a dock.
     */
    V_1782("1782", "Dock, floating"),

    /**
     * Train designed for high speed.
     */
    V_2201("2201", "Train, super express"),

    /**
     * Passenger train that includes carriages for sleeping.
     */
    V_2202("2202", "Train, sleeper"),

    /**
     * A chartered train.
     */
    V_2203("2203", "Train, passenger, hired group"),

    /**
     * Train for carrying freight to the same destination.
     */
    V_2301("2301", "Blocktrain"),

    /**
     * Train for carrying containers.
     */
    V_2302("2302", "Train, container"),

    /**
     * Train with a single wagon for carrying freight.
     */
    V_2303("2303", "Train, with one wagon"),

    /**
     * Train with more than one and less than 20 wagons for carrying freight.
     */
    V_2304("2304", "Train, with more than one and less than 20 wagons"),

    /**
     * Train with more than 20 wagons for carrying freight.
     */
    V_2305("2305", "Train, with more than 20 wagons"),

    /**
     * Automotive tautline truck with trailer capable of carrying household effects.
     */
    V_3100("3100", "Truck, tautliner with removal trailer"),

    /**
     * Automotive tautline vehicle with removable roof and a trailer capable of carrying household
     * effects.
     */
    V_3101("3101", "Truck, tautliner with removable roof and removal trailer"),

    /**
     * Automobile towing a house trailer.
     */
    V_3102("3102", "Car, with caravan"),

    /**
     * Automotive tautline vehicle with a 25 tonne capacity.
     */
    V_3103("3103", "Truck, tautliner, 25 tonne"),

    /**
     * Automotive tautline vehicle with a 25 tonne capacity and a removable roof.
     */
    V_3104("3104", "Truck, tautliner, 25 tonne with removable roof"),

    /**
     * Articulated automotive vehicle with a flat bed and 25 tonne capacity.
     */
    V_3105("3105", "Lorry, articulated, flat bed, 25 tonne"),

    /**
     * Articulated automotive vehicle with a flat bed and 25 tonne capacity with a 10 metre
     * crane attached.
     */
    V_3106("3106", "Lorry, articulated, flat bed, 24 tonne, with 10 metre crane"),

    /**
     * Articulated automotive vehicle with a flat bed and 25 tonne capacity with a 15 metre
     * crane attached.
     */
    V_3107("3107", "Lorry, articulated, flat bed, 24 tonne, with 15 metre crane"),

    /**
     * Articulated automotive vehicle with a flat bed and 25 tonne capacity with an 18 metre
     * crane attached.
     */
    V_3108("3108", "Lorry, articulated, flat bed, 24 tonne, with 18 metre crane"),

    /**
     * Articulated automotive vehicle with a flat bed and 10 tonne capacity.
     */
    V_3109("3109", "Lorry, articulated, flat bed, 10 tonne"),

    /**
     * Automotive tautline vehicle with a 25 tonne capacity and a 90 cubic metre trailer.
     */
    V_3110("3110", "Truck, tautliner, 25 tonne, with 90 cubic metre trailer"),

    /**
     * Automotive tautline vehicle with a 25 tonne capacity and a 120 cubic metre trailer.
     */
    V_3111("3111", "Truck, tautliner, 25 tonne, with 120 cubic metre trailer"),

    /**
     * Automotive vehicle with flat bed and trailer and 10 metre crane.
     */
    V_3112("3112", "Lorry, flat with trailer and 10 metre crane"),

    /**
     * Articulated automotive vehicle with tank designed for carrying liquid or bulk goods.
     */
    V_3113("3113", "Lorry, articulated with tank"),

    /**
     * Automotive vehicle with flat bed and a 15 tonne capacity.
     */
    V_3114("3114", "Lorry, flat, 15 tonne"),

    /**
     * Automotive vehicle with flat bed and a 15 tonne capacity and attached crane.
     */
    V_3115("3115", "Lorry, flat, 15 tonne with crane"),

    /**
     * Automotive vehicle designed to carry temperature-controlled goods.
     */
    V_3116("3116", "Truck, isothermic"),

    /**
     * Automotive vehicle designed to carry refrigerated goods.
     */
    V_3117("3117", "Truck, refrigerated"),

    /**
     * Automotive vehicle designed to carry frozen goods.
     */
    V_3118("3118", "Van, freezer"),

    /**
     * Automotive vehicle designed to carry temperature-controlled goods.
     */
    V_3119("3119", "Van, isothermic"),

    /**
     * Automotive vehicle designed to carry refrigerated goods.
     */
    V_3120("3120", "Van, refrigerated"),

    /**
     * Automotive vehicle designed to carry bulk goods.
     */
    V_3121("3121", "Truck, bulk"),

    /**
     * Automotive vehicle designed with a tip-up capability.
     */
    V_3122("3122", "Truck, tip-up"),

    /**
     * Articulated automotive vehicle designed with a tip-up capability.
     */
    V_3123("3123", "Truck, articulated, tip-up"),

    /**
     * Rigid automotive vehicle designed with a tank.
     */
    V_3124("3124", "Truck, rigid, with tank"),

    /**
     * Automotive vehicle with non-rigid sides.
     */
    V_3125("3125", "Truck, tautliner"),

    /**
     * Automotive tautline vehicle with a removable roof.
     */
    V_3126("3126", "Truck, tautliner, with removable roof"),

    /**
     * Automotive vehicle with a floor that can be opened.
     */
    V_3127("3127", "Truck, with opening floor"),

    /**
     * Automotive vehicle designed to carry frozen goods.
     */
    V_3128("3128", "Truck, freezer"),

    /**
     * A truck with a crane for moving goods, without a trailer.
     */
    V_3129("3129", "Truck, with crane for moving goods, without trailer"),

    /**
     * A truck with a crane for moving goods, with a trailer.
     */
    V_3130("3130", "Truck, with crane for moving goods, with trailer"),

    /**
     * A truck with a crane for lifting goods, without a trailer.
     */
    V_3131("3131", "Truck, with crane for lifting goods, without trailer"),

    /**
     * A truck with a crane for lifting goods, with a trailer.
     */
    V_3132("3132", "Truck, with crane for lifting goods, with trailer"),

    /**
     * Automotive vehicle licensed to ply for hire.
     */
    V_3133("3133", "Taxi cab"),

    /**
     * Automotive vehicle designed for carrying furniture.
     */
    V_3134("3134", "Truck, furniture"),

    /**
     * Automotive vehicle designed for the delivery of fuel from a fixed installation to a
     * means of transport.
     */
    V_3135("3135", "Truck, hydrant"),

    /**
     * Automotive vehicle designed to carry a small number of passengers.
     */
    V_3136("3136", "Car"),

    /**
     * Automotive vehicle with a hydraulic lifting device on the rear of the vehicle for loading
     * and unloading goods.
     */
    V_3137("3137", "Truck, with tail-lift"),

    /**
     * Automotive vehicle designed with fortified body for enhanced protection.
     */
    @Deprecated(forRemoval = true)
    V_3138("3138", "Armoured vehicle"),

    /**
     * Automotive vehicle with raisable work platform.
     */
    V_3201("3201", "Car, elevator"),

    /**
     * Automotive vehicle with an attached trailer for carrying passengers and/or luggage.
     */
    V_3301("3301", "Bus, with trailer"),

    /**
     * Automotive vehicle designed for highway travel.
     */
    V_3302("3302", "Bus, highway"),

    /**
     * Automotive vehicle designed for sightseeing.
     */
    V_3303("3303", "Bus, sightseeing"),

    /**
     * Automotive vehicle designed to carry passengers and their baggage between an airport
     * and a city and return.
     */
    V_3304("3304", "Bus, airport/city"),

    /**
     * Aircraft of unknown type.
     */
    @Deprecated(forRemoval = true)
    V_4000("4000", "Aircraft, type unknown"),

    /**
     * Mail of unknown type
     */
    @Deprecated(forRemoval = true)
    V_5000("5000", "Mail, type unknown"),

    /**
     * Motorized vessel designed for carrying liquid cargo in cargo tanks, type N according
     * to ADN.
     */
    @Deprecated(forRemoval = true)
    V_8021("8021", "Motor tanker, liquid cargo, type N"),

    /**
     * Motorized vessel designed for carrying special liquid chemicals in cargo tanks, type
     * C according to ADN.
     */
    @Deprecated(forRemoval = true)
    V_8022("8022", "Motor tanker, liquid cargo, type C"),

    /**
     * Motorized vessel designed for carrying dry cargo as if liquid in fixed tanks (e.g. cement).
     */
    @Deprecated(forRemoval = true)
    V_8023("8023", "Motor tanker, dry cargo"),

    /**
     * Lighter or Barge designed for carrying liquid cargo in cargo tanks, type N according
     * to ADN, either having no motive power of its own or having only sufficient motive power
     * to perform restricted manoeuvres.
     */
    @Deprecated(forRemoval = true)
    V_8161("8161", "Tank barge, liquid cargo, type N"),

    /**
     * Lighter or Barge designed to for carrying special liquid chemicals in cargo tanks, type
     * C according to ADN, either having no motive power of its own or having only sufficient
     * motive power to perform restricted manoeuvres.
     */
    @Deprecated(forRemoval = true)
    V_8162("8162", "Tank barge, liquid cargo, type C"),

    /**
     * Lighter or Barge designed for carrying dry cargo in fixed tanks as if liquid (e.g. cement),
     * either having no motive power of its own or having only sufficient motive power to perform
     * restricted manoeuvres.
     */
    @Deprecated(forRemoval = true)
    V_8163("8163", "Tank barge, dry cargo"),

    /**
     * Vessel designed for carrying passengers and/or vehicles on regular short voyages.
     */
    @Deprecated(forRemoval = true)
    V_8441("8441", "Ferry"),

    /**
     * Passenger vessel with overnight passenger cabins designed for carrying sick and or disabled
     * people.
     */
    @Deprecated(forRemoval = true)
    V_8442("8442", "Red cross ship"),

    /**
     * Passenger vessel designed for carrying more than 12 passengers accommodated on board.
     */
    @Deprecated(forRemoval = true)
    V_8443("8443", "Cruise ship"),

    /**
     * Passenger vessel designed for carrying more than 12 passengers but without passenger
     * accommodation such as cabins etc.
     */
    @Deprecated(forRemoval = true)
    V_8444("8444", "Passenger ship without accommodation"),

    /**
     * Passenger vessel designed for carrying more than 12 passengers but without passenger
     * accommodation such as cabins etc. capable of reaching speeds over 40 km/h in relation
     * to water.
     */
    @Deprecated(forRemoval = true)
    V_8445("8445", "Day-trip high speed vessel"),

    /**
     * Passenger vessel designed for carrying more than 12 passengers but without passenger
     * accommodation such as cabins etc., hydrofoil.
     */
    @Deprecated(forRemoval = true)
    V_8446("8446", "Day-trip hydrofoil vessel"),

    /**
     * Passenger vessel built and fitted out also with a view to propulsion under sail and
     * designed for carrying more than 12 passengers accommodated on board.
     */
    @Deprecated(forRemoval = true)
    V_8447("8447", "Sailing cruise ship"),

    /**
     * Passenger vessel built and fitted out also with a view to propulsion under sail and
     * designed for carrying more than 12 passengers but without passenger accommodation such
     * as cabins etc.
     */
    @Deprecated(forRemoval = true)
    V_8448("8448", "Sailing passenger ship without accommodation"),

    /**
     * A tender (vessel for logistical support), dealing with the transport of small material
     * and maintenance requirements outside the port area on rivers and other inland waterways.
     */
    @Deprecated(forRemoval = true)
    V_8451("8451", "Service vessel"),

    /**
     * A vessel for the supervision by police for the enforcement of applicable rules and regulations.
     */
    @Deprecated(forRemoval = true)
    V_8452("8452", "Police patrol vessel"),

    /**
     * A port tender (vessel for logistical support), dealing with the transport of material
     * and people within a port area.
     */
    @Deprecated(forRemoval = true)
    V_8453("8453", "Port service vessel"),

    /**
     * A vessel of the competent authority supervising waterway traffic and navigation to ensure
     * safety and adherence to the respective rules and regulations.
     */
    @Deprecated(forRemoval = true)
    V_8454("8454", "Navigation surveillance vessel"),
    ;

    private final String name;
    private final String code;

    TransportMeansTypeCodeType(String code, String name) {
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
