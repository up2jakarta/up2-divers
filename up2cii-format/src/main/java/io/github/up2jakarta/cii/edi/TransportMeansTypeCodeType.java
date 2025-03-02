package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Schema;
import io.github.up2jakarta.cii.edi.adapters.TransportMeansTypeCodeAdapter;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT R28 : Transport Means Type Code.
 * {@see https://unece.org/trade/uncefact/cl-recommendations}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Transport Means Type Code", agency = Agency.UN_ECE, version = "2007")
@Schema(agency = "UN/CEFACT", version = "4.1", date = "2008-08-23")
@XmlJavaTypeAdapter(TransportMeansTypeCodeAdapter.class)
public enum TransportMeansTypeCodeType implements CodeList<TransportMeansTypeCodeType> {

    @Deprecated(since = "D23A", forRemoval = true)
    V_1("1", "Maritime, type unknown"),

    /**
     * Automotive vehicle designed for hauling loads.
     */
    V_31("31", "Truck"),

    /**
     * Automotive vehicle with a tank.
     */
    V_32("32", "Truck, tanker"),

    /**
     * Automotive vehicle with an engine designed for pulling.
     */
    V_33("33", "Tractor"),

    /**
     * Closed automotive vehicle designed for carrying freight.
     */
    V_34("34", "Van"),

    /**
     * Automotive vehicle designed with a tank lifting capability.
     */
    V_35("35", "Tiptanker"),

    /**
     * Automotive vehicle designed for carrying dry bulk cargo.
     */
    V_36("36", "Truck, dry bulk"),

    /**
     * Automotive vehicle designed for carrying containers.
     */
    V_37("37", "Truck, container"),

    /**
     * Automotive vehicle designed for carrying motorcars.
     */
    V_38("38", "Carrier, car"),

    /**
     * Automotive vehicle designed for the carriage of frozen cargo.
     */
    V_39("39", "Truck, reefer"),

    /**
     * Multimodal transport of unknown type.
     */
    V_60("60", "Multimodal, type unknown"),

    /**
     * Fixed transport installation of unknown type.
     */
    V_70("70", "Fixed transport installation, type unknown"),

    /**
     * A line of one or more pipes for continuous transport of liquid or gas commodity.
     */
    V_71("71", "Pipeline"),

    /**
     * A line of one or more cables or wires for continuous transport of electricity.
     */
    V_72("72", "Powerline"),

    /**
     * Vessel of unknown type.
     */
    V_80("80", "Vessel, type unknown"),

    /**
     * Motorized vessel designed for carrying general cargo.
     */
    V_81("81", "Motor freighter"),

    /**
     * Motorized vessel designed for carrying liquid cargo.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_82("82", "Motor tanker"),

    /**
     * Vessel designed for carrying containers.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_83("83", "Container vessel"),

    /**
     * Vessel with tanks designed for carrying gas.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_84("84", "Gas tanker"),

    /**
     * Motorized vessel designed for carrying cargo and capable of towing.
     */
    V_85("85", "Motor freighter, tug"),

    /**
     * Motorized vessel designed for carrying liquid cargo and capable of towing.
     */
    V_86("86", "Motor tanker, tug"),

    /**
     * Motorized vessel designed for carrying general cargo that has one or more vessels alongside.
     */
    V_87("87", "Motor freighter with one or more ships alongside"),

    /**
     * Motorized vessel designed for carrying general cargo alongside a vessel designed for
     * carrying liquid cargo.
     */
    V_88("88", "Motor freighter with tanker"),

    /**
     * Motorized vessel designed for carrying general cargo, pushing one or more vessels also
     * designed for carrying general cargo.
     */
    V_89("89", "Motor freighter pushing one or more freighters"),

    /**
     * Vessel designed to carry general cargo.
     */
    V_150("150", "General cargo vessel"),

    /**
     * Vessel designed to carry unit loads
     */
    V_151("151", "Unit carrier"),

    /**
     * Vessel designed to carry bulk cargo.
     */
    V_152("152", "Bulk carrier"),

    /**
     * Vessel solely equipped with tanks to carry cargo.
     */
    V_153("153", "Tanker"),

    /**
     * Tanker designed to carry liquefied gas.
     */
    V_154("154", "Liquefied gas tanker"),

    /**
     * Tanker designed to carry other special liquids.
     */
    V_155("155", "Other special tanker"),

    /**
     * Vessel designed to carry cargo and passengers.
     */
    V_157("157", "Cargo and passenger vessel"),

    /**
     * Vessel designed to carry more than 12 passengers.
     */
    V_159("159", "Passenger ship"),

    /**
     * Vessel designed to give assistance.
     */
    V_160("160", "Assistance vessel"),

    /**
     * Sea-going vessel, not otherwise specified.
     */
    V_170("170", "Other sea-going vessel"),

    /**
     * Vessel designed to assist in work.
     */
    V_172("172", "Work ship"),

    /**
     * Vessel designed to push other vessels.
     */
    V_173("173", "Push boat"),

    /**
     * Vessel designed to scoop or suck mud or sand.
     */
    V_174("174", "Dredger"),

    /**
     * Vessel designed for fishing.
     */
    V_175("175", "Fishing boat"),

    /**
     * Vessel designed for research and education.
     */
    V_176("176", "Research and education ship"),

    /**
     * Vessel operated by a Navy.
     */
    V_177("177", "Navy vessel"),

    /**
     * Any floating structure.
     */
    V_178("178", "Structure, floating"),

    /**
     * Vessel designed for recreation.
     */
    V_180("180", "Pleasure boat"),

    /**
     * Vessel designed for speed, often used for recreation.
     */
    V_181("181", "Speedboat"),

    /**
     * Vessel designed primarily for sailing outfitted with an auxiliary motor.
     */
    V_182("182", "Sailing boat with auxiliary motor"),

    /**
     * A specific type of vessel mostly used for pleasure and designed for sailing.
     */
    V_183("183", "Sailing yacht"),

    /**
     * Vessel designed for sport fishing.
     */
    V_184("184", "Boat for sport fishing"),

    /**
     * Vessel longer than 20 metres, designed for recreation.
     */
    V_185("185", "Craft, pleasure, longer than 20 metres"),

    /**
     * Vessel designed for recreation, not otherwise specified.
     */
    V_189("189", "Craft, other, recreational"),

    /**
     * Fast, all-purpose vessel.
     */
    V_190("190", "Fast ship"),

    /**
     * Vessel with wing-like structure for skimming at high speed.
     */
    V_191("191", "Hydrofoil"),

    /**
     * Fast vessel designed with two parallel hulls.
     */
    V_192("192", "Catamaran, fast"),

    /**
     * One or more rail wagons pulled or pushed by one or more locomotive units, or self-propelled,
     * that move over rail tracks.
     */
    V_210("210", "Train, railroad"),

    /**
     * Train designed to carry passengers.
     */
    V_220("220", "Train, passenger"),

    /**
     * Train for carrying freight.
     */
    V_230("230", "Train, freight"),

    /**
     * Automotive vehicle designed for carrying mail.
     */
    V_310("310", "Truck, mail"),

    /**
     * Automotive vehicle designed with a cargo-dumping capability.
     */
    V_311("311", "Truck dump"),

    /**
     * Automotive vehicle designed for lifting cargo and heavy objects.
     */
    V_312("312", "Truck, forklift"),

    /**
     * Automotive vehicle designed for shoveling sand and other bulk material.
     */
    V_313("313", "Loader, shovel"),

    /**
     * Automotive vehicle designed with a fixed platform.
     */
    V_314("314", "Truck, platform, fixed"),

    /**
     * Automotive vehicle designed for lifting and transporting containers.
     */
    V_315("315", "Carrier, straddle"),

    /**
     * Automotive vehicle with cargo crane.
     */
    V_320("320", "Crane, mobile"),

    /**
     * Automotive vehicle designed for carrying more than 8 passengers including the driver.
     */
    V_330("330", "Bus"),

    /**
     * Automotive vehicle designed for making fast deliveries.
     */
    V_341("341", "Van, delivery"),

    /**
     * Automotive vehicle designed for light carriage.
     */
    V_342("342", "Van, light"),

    /**
     * Automotive vehicle designed for carrying furniture.
     */
    V_343("343", "Van, furniture"),

    /**
     * Automotive vehicle designed for towing one or more trailers.
     */
    V_360("360", "Tractor, industrial"),

    /**
     * Automotive vehicle designed for carrying frozen goods with a trailer designed for carrying
     * temperature-controlled goods.
     */
    V_362("362", "Truck, freezer with isothermic trailer,"),

    /**
     * Automotive vehicle with trailer designed to carry temperature-controlled goods.
     */
    V_363("363", "Truck, isothermic with isothermic trailer"),

    /**
     * Automotive vehicle designed for carrying refrigerated goods with a trailer designed
     * for carrying temperature-controlled goods.
     */
    V_364("364", "Truck, refrigerated with isothermic trailer"),

    /**
     * Automotive vehicle designed for carrying frozen goods with a trailer designed for carrying
     * refrigerated goods.
     */
    V_365("365", "Truck, freezer with refrigerated trailer"),

    /**
     * Automotive vehicle designed to carry temperature-controlled goods with a trailer designed
     * to carry refrigerated goods.
     */
    V_366("366", "Truck, isothermic with refrigerated trailer"),

    /**
     * Automotive vehicle with an opening floor with an extendable trailer.
     */
    V_367("367", "Truck, opening floor, with extendable trailer"),

    /**
     * Rigid automotive vehicle designed with a tank with a tank trailer.
     */
    V_368("368", "Truck, rigid, with tank and tank trailer"),

    /**
     * Automotive vehicle designed for bulk carrying with a tank trailer.
     */
    V_369("369", "Truck, bulk with tank trailer"),

    /**
     * Rigid automotive vehicle designed with a tank with a trailer capable of carrying bulk
     * cargo and liquid.
     */
    V_370("370", "Truck, rigid with tank and bulk trailer"),

    /**
     * Automotive vehicle and trailer both designed for carrying bulk cargo.
     */
    V_371("371", "Truck, bulk with bulk trailer"),

    /**
     * Automotive tautliner vehicle with extendable trailer.
     */
    V_372("372", "Truck, tautliner with extendable trailer"),

    /**
     * Automotive tautliner vehicle with removable roof and extendable trailer.
     */
    V_373("373", "Truck, tautliner with removable roof and extendable trailer"),

    /**
     * Automotive vehicle designed for carrying bulk cargo with an extendable trailer.
     */
    V_374("374", "Truck, bulk truck with extendable trailer"),

    /**
     * Automotive vehicle designed for carrying refrigerated goods with a trailer designed
     * for carrying frozen goods.
     */
    V_375("375", "Truck, refrigerated with freezer trailer"),

    /**
     * Automotive vehicle designed for carrying temperature-controlled goods with a trailer
     * designed for carrying frozen goods.
     */
    V_376("376", "Truck, isothermic with freezer trailer"),

    /**
     * Automotive vehicle designed for carrying furniture with a trailer.
     */
    V_377("377", "Truck, furniture with trailer"),

    /**
     * Automotive tautliner vehicle with trailer designed for carrying furniture.
     */
    V_378("378", "Truck, tautliner with furniture trailer"),

    /**
     * Automotive tautliner vehicle designed with a removable roof with a trailer designed
     * for carrying furniture.
     */
    V_379("379", "Truck, tautliner, removable roof with furniture trailer"),

    /**
     * Automotive vehicle designed with a tip-up capability with a gondola trailer.
     */
    V_380("380", "Truck, tip-up with gondola trailer"),

    /**
     * Automotive tautliner vehicle with a gondola trailer.
     */
    V_381("381", "Truck, tautliner with gondola trailer"),

    /**
     * Automotive tautliner vehicle with removable roof and a gondola trailer.
     */
    V_382("382", "Truck, tautliner, with removable roof and gondola trailer"),

    /**
     * Automotive vehicle with an opening floor and with a gondola trailer.
     */
    V_383("383", "Truck, opening-floor with gondola trailer"),

    /**
     * Automotive vehicle designed for carrying bulk cargo with a gondola trailer.
     */
    V_384("384", "Truck, bulk with gondola trailer"),

    /**
     * Automotive vehicle designed with a tip-up capability with an extendable gondola trailer.
     */
    V_385("385", "Truck, tip-up with extendable gondola trailer"),

    /**
     * Automotive tautliner vehicle with an extendable gondola trailer.
     */
    V_386("386", "Truck, tautliner with extendable gondola trailer"),

    /**
     * Automotive tautliner vehicle designed with a removable roof and with an extendable
     * gondola trailer.
     */
    V_387("387", "Truck, tautliner, removable roof with extendable gondola trailer"),

    /**
     * Automotive tautliner vehicle designed with an opening floor and with an extendable
     * gondola trailer.
     */
    V_388("388", "Truck, opening floor with extendable gondola trailer"),

    /**
     * Automotive vehicle designed for carrying bulk cargo with an extendable gondola trailer.
     */
    V_389("389", "Truck, bulk with extendable gondola trailer"),

    /**
     * Automotive vehicle designed with a tip-up capability with an opening-floor trailer.
     */
    V_390("390", "Truck, tip-up truck with opening-floor trailer"),

    /**
     * Automotive tautliner vehicle with opening-floor trailer.
     */
    V_391("391", "Truck, tautliner with opening-floor trailer"),

    /**
     * Automotive tautliner vehicle with a removable roof, with an opening-floor trailer.
     */
    V_392("392", "Truck, tautliner, removable roof, with opening-floor trailer"),

    /**
     * Automotive vehicle and trailer both with opening floors.
     */
    V_393("393", "Truck, opening-floor with opening-floor trailer"),

    /**
     * Automotive vehicle designed for carrying bulk cargo with an opening-floor trailer.
     */
    V_394("394", "Truck, bulk truck with opening-floor trailer"),

    /**
     * Automotive vehicle designed to pull a trailer, with a trailer attached.
     */
    V_395("395", "Truck, with trailer"),

    /**
     * Automotive vehicle with a tilt capability with a trailer also with a tilt capability.
     */
    V_396("396", "Truck, tilt, with tilt trailer"),

    /**
     * Automotive vehicle designed to carry refrigerated goods with a trailer also capable
     * of carrying refrigerated goods.
     */
    V_397("397", "Truck, refrigerated, with refrigerated trailer"),

    /**
     * Automotive vehicle capable of carrying frozen goods with a trailer also capable of
     * carrying frozen goods.
     */
    V_398("398", "Truck, freezer with freezer trailer"),

    /**
     * Automotive vehicle designed to carry household effects with a trailer also capable
     * of carrying household effects.
     */
    V_399("399", "Truck, removal with removal trailer"),

    @Deprecated(since = "D23A", forRemoval = true)
    V_802("802", "Motor tanker"),

    @Deprecated(since = "D23A", forRemoval = true)
    V_803("803", "Container vessel"),

    @Deprecated(since = "D23A", forRemoval = true)
    V_804("804", "Gas tanker"),

    /**
     * Motorized vessel designed for carrying general cargo, pushing at least one vessel designed
     * to carry liquid cargo.
     */
    V_810("810", "Motor freighter pushing at least one tank-ship"),

    /**
     * Vessel designed to push or pull another vessel that is also capable of carrying general
     * cargo.
     */
    V_811("811", "Tug, freighter"),

    /**
     * Vessel designed to push or pull another vessel also capable of carrying liquid cargo.
     */
    V_812("812", "Tug, tanker"),

    /**
     * Vessel designed to push or pull another vessel that is also capable of carrying general
     * cargo tied to one or more other vessels.
     */
    V_813("813", "Tug, freighter, coupled"),

    /**
     * Vessel designed to push or pull another vessel that is also capable of carrying either
     * general or liquid cargo tied to one or more other vessels.
     */
    V_814("814", "Tug, freighter/tanker, coupled"),

    /**
     * Lighter designed for carrying general cargo.
     */
    V_815("815", "Freightbarge"),

    /**
     * Lighter designed for carrying liquid cargo.
     */
    V_816("816", "Tankbarge"),

    /**
     * Lighter designed for carrying containers.
     */
    V_817("817", "Freightbarge with containers"),

    /**
     * Lighter designed for carrying gas.
     */
    V_818("818", "Tankbarge, gas"),

    /**
     * Vessel designed for pushing/towing, facilitating the movement of one cargo barge.
     */
    V_821("821", "Pushtow, one cargo barge"),

    /**
     * Combination designed for pushing/towing, facilitating the movement of two cargo barges
     */
    V_822("822", "Pushtow, two cargo barges"),

    /**
     * Combination designed for pushing/towing, facilitating the movement of three cargo barges
     */
    V_823("823", "Pushtow, three cargo barges"),

    /**
     * Combination designed for pushing/towing, facilitating the movement of four cargo barges
     */
    V_824("824", "Pushtow, four cargo barges"),

    /**
     * Combination designed for pushing/towing, facilitating the movement of five cargo barges.
     */
    V_825("825", "Pushtow, five cargo barges"),

    /**
     * Combination designed for pushing/towing, facilitating the movement of six cargo barges.
     */
    V_826("826", "Pushtow, six cargo barges"),

    /**
     * Combination designed for pushing/towing, facilitating the movement of seven cargo barges.
     */
    V_827("827", "Pushtow, seven cargo barges"),

    /**
     * Combination designed for pushing/towing, facilitating the movement of eight cargo barges.
     */
    V_828("828", "Pushtow, eight cargo barges"),

    /**
     * Combination designed for pushing/towing, facilitating the movement of nine or more
     * cargo barges.
     */
    V_829("829", "Pushtow, nine cargo barges"),

    /**
     * Combination designed for pushing/towing, moving one tanker or gas barge.
     */
    V_831("831", "Pushtow, one gas/tank barge"),

    /**
     * Combination designed for pushing/towing, moving two barges of which at least one tanker
     * or gas barge.
     */
    V_832("832", "Pushtow, two barges at least one tanker or gas barge"),

    /**
     * Combination designed for pushing/towing, moving three barges of which at least one
     * is a tanker or gas barge.
     */
    V_833("833", "Pushtow, three barges at least one tanker or gas barge"),

    /**
     * Combination designed for pushing/towing, moving four barges of which at least one is
     * a tanker or gas barge.
     */
    V_834("834", "Pushtow, four barges at least one tanker or gas barge"),

    /**
     * Combination designed for pushing/towing, moving five barges of which at least one is
     * a tanker of gas barge.
     */
    V_835("835", "Pushtow, five barges at least one tanker or gas barge"),

    /**
     * Combination designed for pushing/towing, moving six barges of which at least one is
     * a tanker or gas barge.
     */
    V_836("836", "Pushtow, six barges at least one tanker or gas barge"),

    /**
     * Combination designed for pushing/towing, moving seven barges of which at least one
     * is a tanker or gas barge.
     */
    V_837("837", "Pushtow, seven barges at least one tanker or gas barge"),

    /**
     * Combination designed for pushing/towing, moving eight barges of which at least one
     * is a tanker or gas barge.
     */
    V_838("838", "Pushtow, eight barges at least one tanker or gas barge"),

    /**
     * Combination designed for pushing/towing, moving nine or more barges of which at least
     * one is a tanker or gas barge.
     */
    V_839("839", "Pushtow, nine or more barges at least one tanker or gas barge"),

    /**
     * Vessel designed for pushing another vessel that is the only boat used for a tow.
     */
    V_840("840", "Tug, single"),

    /**
     * Vessel designed for pushing another vessel that is involved in one or more concurrent
     * tows.
     */
    V_841("841", "Tug, one or more tows"),

    /**
     * Vessel designed for pushing another vessel that is assisting one vessel or a combination
     * of vessels or tugs and vessels.
     */
    V_842("842", "Tug, assisting a vessel or linked combination"),

    /**
     * Vessel designed for pushing.
     */
    V_843("843", "Pushboat, single"),

    /**
     * Vessels designed for carrying passengers.
     */
    V_844("844", "Passenger ship, ferry, red cross ship, cruise ship"),

    /**
     * Vessel designed to perform a specific dedicated service.
     */
    V_845("845", "Service vessel, police patrol, port services"),

    /**
     * Vessel designed to perform a specific type of work.
     */
    V_846("846", "Vessel, work maintenance craft, floating derrick, cable-ship, buoy-ship, dredge."),

    /**
     * An object in tow that is not otherwise specified.
     */
    V_847("847", "Object, towed, not otherwise specified."),

    /**
     * Vessel designed for fishing.
     */
    V_848("848", "Fishing boat"),

    /**
     * Vessel designed for carrying and delivering bunkers.
     */
    V_849("849", "Bunkership"),

    /**
     * Vessel designed to carry liquid or bulk chemicals.
     */
    V_850("850", "Barge, tanker, chemical"),

    /**
     * A floating object that is not otherwise specified.
     */
    V_851("851", "Object, not otherwise specified."),

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
    V_4000("4000", "Aircraft, type unknown"),

    /**
     * Mail of unknown type
     */
    V_5000("5000", "Mail, type unknown"),

    /**
     * Motorized vessel designed for carrying liquid cargo in cargo tanks, type N according
     * to ADN.
     */
    V_8021("8021", "Motor tanker, liquid cargo, type N"),

    /**
     * Motorized vessel designed for carrying special liquid chemicals in cargo tanks, type
     * C according to ADN.
     */
    V_8022("8022", "Motor tanker, liquid cargo, type C"),

    /**
     * Motorized vessel designed for carrying dry cargo as if liquid in fixed tanks (e.g.
     * cement).
     */
    V_8023("8023", "Motor tanker, dry cargo"),

    /**
     * Lighter or Barge designed for carrying liquid cargo in cargo tanks, type N according
     * to ADN, either having no motive power of its own or having only sufficient motive power
     * to perform restricted manoeuvres.
     */
    V_8161("8161", "Tank barge, liquid cargo, type N"),

    /**
     * Lighter or Barge designed to for carrying special liquid chemicals in cargo tanks,
     * type C according to ADN, either having no motive power of its own or having only sufficient
     * motive power to perform restricted manoeuvres.
     */
    V_8162("8162", "Tank barge, liquid cargo, type C"),

    /**
     * Lighter or Barge designed for carrying dry cargo in fixed tanks as if liquid (e.g.
     * cement), either having no motive power of its own or having only sufficient motive
     * power to perform restricted manoeuvres.
     */
    V_8163("8163", "Tank barge, dry cargo"),

    /**
     * Vessel designed for carrying passengers and/or vehicles on regular short voyages.
     */
    V_8441("8441", "Ferry"),

    /**
     * Passenger vessel with overnight passenger cabins designed for carrying sick and or
     * disabled people.
     */
    V_8442("8442", "Red cross ship"),

    /**
     * Passenger vessel designed for carrying more than 12 passengers accommodated on board.
     */
    V_8443("8443", "Cruise ship"),

    /**
     * Passenger vessel designed for carrying more than 12 passengers but without passenger
     * accommodation such as cabins etc.
     */
    V_8444("8444", "Passenger ship without accommodation"),

    /**
     * Passenger vessel designed for carrying more than 12 passengers but without passenger
     * accommodation such as cabins etc. capable of reaching speeds over 40 km/h in relation
     * to water.
     */
    V_8445("8445", "Day-trip high speed vessel"),

    /**
     * Passenger vessel designed for carrying more than 12 passengers but without passenger
     * accommodation such as cabins etc., hydrofoil.
     */
    V_8446("8446", "Day-trip hydrofoil vessel"),

    /**
     * Passenger vessel built and fitted out also with a view to propulsion under sail and
     * designed for carrying more than 12 passengers accommodated on board.
     */
    V_8447("8447", "Sailing cruise ship"),

    /**
     * Passenger vessel built and fitted out also with a view to propulsion under sail and
     * designed for carrying more than 12 passengers but without passenger accommodation such
     * as cabins etc.
     */
    V_8448("8448", "Sailing passenger ship without accommodation"),

    /**
     * A tender (vessel for logistical support), dealing with the transport of small material
     * and maintenance requirements outside the port area on rivers and other inland waterways.
     */
    V_8451("8451", "Service vessel"),

    /**
     * A vessel for the supervision by police for the enforcement of applicable rules and
     * regulations.
     */
    V_8452("8452", "Police patrol vessel"),

    /**
     * A port tender (vessel for logistical support), dealing with the transport of material
     * and people within a port area.
     */
    V_8453("8453", "Port service vessel"),

    /**
     * A vessel of the competent authority supervising waterway traffic and navigation to
     * ensure safety and adherence to the respective rules and regulations.
     */
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
