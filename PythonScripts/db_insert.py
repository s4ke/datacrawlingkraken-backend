import csv
import pandas as pd
import matplotlib.pyplot as plt
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.automap import automap_base
from sqlalchemy import text
from declarative import Base, ReadingAverage, Reading

csvnames = ["agg/1.csv", "agg/2.csv"]
streamcsv = ["stream/data.csv"]

# engine = create_engine('sqlite:///sqlalchemy_sqllite.db')
# engine = create_engine('mysql+mysqlconnector://mysql:mysql@localhost:8182')
# engine = create_engine('mysql+pymysql://mysql:mysql@localhost:12346/datacrawlingkraken')
engine = create_engine('mysql+pymysql://mysql:mysql@192.168.0.102:12346/datacrawlingkraken')
print("Engine created....")

Base.metadata.create_all(engine)

print("Create all tables done....")
DBSession = sessionmaker(bind=engine)
session = DBSession()


def readingavgfromcsv():
    print("READING AVG")
    for path in csvnames:
        querytext = ""
        testdict = {}
        # print("'")
        x = 0
        with open(path, 'r') as f:
            data = csv.DictReader(f)
            for name in data.fieldnames:
                print("---")
                print(name + "=float(testdict['" + name.lower() + "']),")

            for row in data:
                # print(row)
                for k, v in row.items():
                    # print(k + " " + v)
                    if not v:
                        v = 0
                    testdict[k] = v
                # print(testdict)
                new_row = ReadingAverage(Vehicle_Number=(testdict['Vehicle_Number']),
                                         Ambiant_Pressure=float(testdict['Ambiant_Pressure']),
                                         Canister_Controler=float(testdict['Canister_Controler']),
                                         Current_for_Injector_Opening=float(testdict['Current_for_Injector_Opening']),
                                         Distance_Km=float(testdict['Distance_Km']),
                                         Lambda_Adapation=float(testdict['Lambda_Adapation']),
                                         Gear_Engaged=float(testdict['Gear_Engaged']),
                                         Lambda_Control_1=float(testdict['Lambda_Control_1']),
                                         Lambda_Control_2=float(testdict['Lambda_Control_2']),
                                         MAF_Before_Compressor=float(testdict['MAF_Before_Compressor']),
                                         Engine_MAF=float(testdict['Engine_MAF']),
                                         Fuel_Mass_Adapation=float(testdict['Fuel_Mass_Adapation']),
                                         Engine_Speed=float(testdict['Engine_Speed']),
                                         Turbo_Turbine_Speed=float(testdict['Turbo_Turbine_Speed']),
                                         Injection_Number=float(testdict['Injection_Number']),
                                         Fuel_Pressure_at_Inlet_Pump=float(testdict['Fuel_Pressure_at_Inlet_Pump']),
                                         Fuel_Pressure=float(testdict['Fuel_Pressure']),
                                         Turbo_Waste_Gate_Position=float(testdict['Turbo_Waste_Gate_Position']),
                                         Pedal_Position=float(testdict['Pedal_Position']),
                                         Turbo_Waste_Gate_Controler=float(testdict['Turbo_Waste_Gate_Controler']),
                                         Water_Pump_Controler=float(testdict['Water_Pump_Controler']),
                                         Engine_Thermostat_Controler=float(testdict['Engine_Thermostat_Controler']),
                                         Engine_Air_Throttle_Controler=float(testdict['Engine_Air_Throttle_Controler']),
                                         Fuel_Pump_Delivery=float(testdict['Fuel_Pump_Delivery']),
                                         Segment_Number=float(testdict['Segment_Number']),
                                         Injector_Adapation1=float(testdict['Injector_Adapation1']),
                                         Injector_Adapation2=float(testdict['Injector_Adapation2']),
                                         Injector_Adapation3=float(testdict['Injector_Adapation3']),
                                         Injector_Adapation4=float(testdict['Injector_Adapation4']),
                                         Injector_Adapation5=float(testdict['Injector_Adapation5']),
                                         Injector_Adapation6=float(testdict['Injector_Adapation6']),
                                         Injector_Adapation7=float(testdict['Injector_Adapation7']),
                                         Injector_Adapation8=float(testdict['Injector_Adapation8']),
                                         Injector_Adapation9=float(testdict['Injector_Adapation9']),
                                         Injector_Adapation10=float(testdict['Injector_Adapation10']),
                                         Injector_Adapation11=float(testdict['Injector_Adapation11']),
                                         Injector_Adapation12=float(testdict['Injector_Adapation12']),
                                         Injector_Adapation13=float(testdict['Injector_Adapation13']),
                                         Injector_Adapation14=float(testdict['Injector_Adapation14']),
                                         Injector_Adapation15=float(testdict['Injector_Adapation15']),
                                         Injector_Adapation16=float(testdict['Injector_Adapation16']),
                                         Injector_Adapation17=float(testdict['Injector_Adapation17']),
                                         Injector_Adapation18=float(testdict['Injector_Adapation18']),
                                         Injector_Adapation19=float(testdict['Injector_Adapation19']),
                                         Injector_Adapation20=float(testdict['Injector_Adapation20']),
                                         Current_Time_for_Injector=float(testdict['Current_Time_for_Injector']),
                                         Pump_Adaptation_2=float(testdict['Pump_Adaptation_2']),
                                         Ambiant_Temperature=float(testdict['Ambiant_Temperature']),
                                         Engine_Coolant_Tempertaure=float(testdict['Engine_Coolant_Tempertaure']),
                                         ECU_Temperature=float(testdict['ECU_Temperature']),
                                         TUrbo_Temperature_before_Turbine=float(
                                             testdict['TUrbo_Temperature_before_Turbine']),
                                         Fuel_Temperature_Before_Pump=float(testdict['Fuel_Temperature_Before_Pump']),
                                         Fuel_Temperature_at_Pump_Outlet=float(
                                             testdict['Fuel_Temperature_at_Pump_Outlet']),
                                         Fuel_Temperature_In_Rail=float(testdict['Fuel_Temperature_In_Rail']),
                                         Engine_Inlet_Temperture=float(testdict['Engine_Inlet_Temperture']),
                                         time_30_s=float(testdict['time_30_s']),
                                         timestamp=int(testdict['timestamp']),
                                         Engine_Oil_Temperature=float(testdict['Engine_Oil_Temperature']),
                                         Trt=float(testdict['Trt']),
                                         Battery_Voltage=float(testdict['Battery_Voltage']),
                                         Voltage_Boost_Injector_control=float(
                                             testdict['Voltage_Boost_Injector_control']),
                                         Pump_Adaptation_1=float(testdict['Pump_Adaptation_1']),
                                         Waste_Gate_Adapatation_1=float(testdict['Waste_Gate_Adapatation_1']),
                                         Waste_Gate_Adapatation_2=float(testdict['Waste_Gate_Adapatation_2']),
                                         Vehicle_Speed=float(testdict['Vehicle_Speed']))
                # print("---")
                session.add(new_row)
                x = x + 1
                if (x % 50 == 0):
                    session.commit()
            session.commit()
            print("CSV ADDED")


def readingstreamfromcsv():
    print("READING STREAM")
    for path in streamcsv:
        querytext = ""
        testdict = {}
        # print("'")
        x = 0
        with open(path, 'r') as f:
            data = csv.DictReader(f)

            #for name in data.fieldnames:
                # print("---")
                #print(name + "=float(testdict['" + name.lower() + "']),")
                #print(name + "=Column(Float)")
            for row in data:
                # print(row)
                for k, v in row.items():
                    # print(k + " " + v)
                    if not v:
                        v = 0
                    testdict[k.lower()] = v
                # print(testdict)
                new_row = Reading(vehicle_number=(testdict['vehicle_number']),
                                         ambiant_pressure=float(testdict['ambiant_pressure']),
                                         canister_controler=float(testdict['canister_controler']),
                                         current_for_injector_opening=float(testdict['current_for_injector_opening']),
                                         distance_km=float(testdict['distance_km']),
                                         lambda_adapation=float(testdict['lambda_adapation']),
                                         gear_engaged=float(testdict['gear_engaged']),
                                         lambda_control_1=float(testdict['lambda_control_1']),
                                         lambda_control_2=float(testdict['lambda_control_2']),
                                         maf_before_compressor=float(testdict['maf_before_compressor']),
                                         engine_maf=float(testdict['engine_maf']),
                                         fuel_mass_adapation=float(testdict['fuel_mass_adapation']),
                                         engine_speed=float(testdict['engine_speed']),
                                         turbo_turbine_speed=float(testdict['turbo_turbine_speed']),
                                         injection_number=float(testdict['injection_number']),
                                         fuel_pressure_at_inlet_pump=float(testdict['fuel_pressure_at_inlet_pump']),
                                         fuel_pressure=float(testdict['fuel_pressure']),
                                         turbo_waste_gate_position=float(testdict['turbo_waste_gate_position']),
                                         pedal_position=float(testdict['pedal_position']),
                                         turbo_waste_gate_controler=float(testdict['turbo_waste_gate_controler']),
                                         water_pump_controler=float(testdict['water_pump_controler']),
                                         engine_thermostat_controler=float(testdict['engine_thermostat_controler']),
                                         engine_air_throttle_controler=float(testdict['engine_air_throttle_controler']),
                                         fuel_pump_delivery=float(testdict['fuel_pump_delivery']),
                                         segment_number=float(testdict['segment_number']),
                                         injector_adapation1=float(testdict['injector_adapation1']),
                                         injector_adapation2=float(testdict['injector_adapation2']),
                                         injector_adapation3=float(testdict['injector_adapation3']),
                                         injector_adapation4=float(testdict['injector_adapation4']),
                                         injector_adapation5=float(testdict['injector_adapation5']),
                                         injector_adapation6=float(testdict['injector_adapation6']),
                                         injector_adapation7=float(testdict['injector_adapation7']),
                                         injector_adapation8=float(testdict['injector_adapation8']),
                                         injector_adapation9=float(testdict['injector_adapation9']),
                                         injector_adapation10=float(testdict['injector_adapation10']),
                                         injector_adapation11=float(testdict['injector_adapation11']),
                                         injector_adapation12=float(testdict['injector_adapation12']),
                                         injector_adapation13=float(testdict['injector_adapation13']),
                                         injector_adapation14=float(testdict['injector_adapation14']),
                                         injector_adapation15=float(testdict['injector_adapation15']),
                                         injector_adapation16=float(testdict['injector_adapation16']),
                                         injector_adapation17=float(testdict['injector_adapation17']),
                                         injector_adapation18=float(testdict['injector_adapation18']),
                                         injector_adapation19=float(testdict['injector_adapation19']),
                                         injector_adapation20=float(testdict['injector_adapation20']),
                                         current_time_for_injector=float(testdict['current_time_for_injector']),
                                         pump_adaptation_2=float(testdict['pump_adaptation_2']),
                                         ambiant_temperature=float(testdict['ambiant_temperature']),
                                         engine_coolant_tempertaure=float(testdict['engine_coolant_tempertaure']),
                                         ecu_temperature=float(testdict['ecu_temperature']),
                                         turbo_temperature_before_turbine=float(
                                             testdict['turbo_temperature_before_turbine']),
                                         fuel_temperature_before_pump=float(testdict['fuel_temperature_before_pump']),
                                         fuel_temperature_at_pump_outlet=float(
                                             testdict['fuel_temperature_at_pump_outlet']),
                                         fuel_temperature_in_rail=float(testdict['fuel_temperature_in_rail']),
                                         engine_inlet_temperture=float(testdict['engine_inlet_temperture']),
                                         time_30_s=float(testdict['time_30_s']),
                                         timestamp=int(testdict['timestamp']),
                                         engine_oil_temperature=float(testdict['engine_oil_temperature']),
                                         trt=float(testdict['trt']),
                                         battery_voltage=float(testdict['battery_voltage']),
                                         voltage_boost_injector_control=float(
                                             testdict['voltage_boost_injector_control']),
                                         pump_adaptation_1=float(testdict['pump_adaptation_1']),
                                         waste_gate_adapatation_1=float(testdict['waste_gate_adapatation_1']),
                                         waste_gate_adapatation_2=float(testdict['waste_gate_adapatation_2']),
                                         vehicle_speed=float(testdict['vehicle_speed']),
                                         vin=(testdict['vin']))
                # print("---")
                session.add(new_row)
                x = x + 1
                if (x % 200 == 0):
                    session.commit()
            session.commit()
            print("STREAM ADDED")


readingstreamfromcsv()
# for path in csvnames:
# 	querytext=""
# 	testdict={}
# 	#print("'")
# 	x=0
# 	with open(path,'r') as f:
# 		data = csv.DictReader(f)
# 		#for name in data.fieldnames:
# 			#print("---")
# 			#print(name+"=float(testdict['"+name+"']),")
#
# 		for row in data:
# 			#print(row)
# 			for k,v in row.items():
# 				#print(k + " " + v)
# 				if not v:
# 					v = 0
# 				testdict[k] = v
# 			#print(testdict)
# 			new_row = ReadingAverage(Vehicle_Number=(testdict['Vehicle_Number']),
# 									 Ambiant_Pressure=float(testdict['Ambiant_Pressure']),
# 									 Canister_Controler=float(testdict['Canister_Controler']),
# 									 Current_for_Injector_Opening=float(testdict['Current_for_Injector_Opening']),
# 									 Distance_Km=float(testdict['Distance_Km']),
# 									 Lambda_Adapation=float(testdict['Lambda_Adapation']),
# 									 Gear_Engaged=float(testdict['Gear_Engaged']),
# 									 Lambda_Control_1=float(testdict['Lambda_Control_1']),
# 									 Lambda_Control_2=float(testdict['Lambda_Control_2']),
# 									 MAF_Before_Compressor=float(testdict['MAF_Before_Compressor']),
# 									 Engine_MAF=float(testdict['Engine_MAF']),
# 									 Fuel_Mass_Adapation=float(testdict['Fuel_Mass_Adapation']),
# 									 Engine_Speed=float(testdict['Engine_Speed']),
# 									 Turbo_Turbine_Speed=float(testdict['Turbo_Turbine_Speed']),
# 									 Injection_Number=float(testdict['Injection_Number']),
# 									 Fuel_Pressure_at_Inlet_Pump=float(testdict['Fuel_Pressure_at_Inlet_Pump']),
# 									 Fuel_Pressure=float(testdict['Fuel_Pressure']),
# 									 Turbo_Waste_Gate_Position=float(testdict['Turbo_Waste_Gate_Position']),
# 									 Pedal_Position=float(testdict['Pedal_Position']),
# 									 Turbo_Waste_Gate_Controler=float(testdict['Turbo_Waste_Gate_Controler']),
# 									 Water_Pump_Controler=float(testdict['Water_Pump_Controler']),
# 									 Engine_Thermostat_Controler=float(testdict['Engine_Thermostat_Controler']),
# 									 Engine_Air_Throttle_Controler=float(testdict['Engine_Air_Throttle_Controler']),
# 									 Fuel_Pump_Delivery=float(testdict['Fuel_Pump_Delivery']),
# 									 Segment_Number=float(testdict['Segment_Number']),
# 									 Injector_Adapation1=float(testdict['Injector_Adapation1']),
# 									 Injector_Adapation2=float(testdict['Injector_Adapation2']),
# 									 Injector_Adapation3=float(testdict['Injector_Adapation3']),
# 									 Injector_Adapation4=float(testdict['Injector_Adapation4']),
# 									 Injector_Adapation5=float(testdict['Injector_Adapation5']),
# 									 Injector_Adapation6=float(testdict['Injector_Adapation6']),
# 									 Injector_Adapation7=float(testdict['Injector_Adapation7']),
# 									 Injector_Adapation8=float(testdict['Injector_Adapation8']),
# 									 Injector_Adapation9=float(testdict['Injector_Adapation9']),
# 									 Injector_Adapation10=float(testdict['Injector_Adapation10']),
# 									 Injector_Adapation11=float(testdict['Injector_Adapation11']),
# 									 Injector_Adapation12=float(testdict['Injector_Adapation12']),
# 									 Injector_Adapation13=float(testdict['Injector_Adapation13']),
# 									 Injector_Adapation14=float(testdict['Injector_Adapation14']),
# 									 Injector_Adapation15=float(testdict['Injector_Adapation15']),
# 									 Injector_Adapation16=float(testdict['Injector_Adapation16']),
# 									 Injector_Adapation17=float(testdict['Injector_Adapation17']),
# 									 Injector_Adapation18=float(testdict['Injector_Adapation18']),
# 									 Injector_Adapation19=float(testdict['Injector_Adapation19']),
# 									 Injector_Adapation20=float(testdict['Injector_Adapation20']),
# 									 Current_Time_for_Injector=float(testdict['Current_Time_for_Injector']),
# 									 Pump_Adaptation_2=float(testdict['Pump_Adaptation_2']),
# 									 Ambiant_Temperature=float(testdict['Ambiant_Temperature']),
# 									 Engine_Coolant_Tempertaure=float(testdict['Engine_Coolant_Tempertaure']),
# 									 ECU_Temperature=float(testdict['ECU_Temperature']),
# 									 TUrbo_Temperature_before_Turbine=float(testdict['TUrbo_Temperature_before_Turbine']),
# 									 Fuel_Temperature_Before_Pump=float(testdict['Fuel_Temperature_Before_Pump']),
# 									 Fuel_Temperature_at_Pump_Outlet=float(testdict['Fuel_Temperature_at_Pump_Outlet']),
# 									 Fuel_Temperature_In_Rail=float(testdict['Fuel_Temperature_In_Rail']),
# 									 Engine_Inlet_Temperture=float(testdict['Engine_Inlet_Temperture']),
# 									 time_30_s=float(testdict['time_30_s']),
# 									 timestamp=int(testdict['timestamp']),
# 									 Engine_Oil_Temperature=float(testdict['Engine_Oil_Temperature']),
# 									 Trt=float(testdict['Trt']),
# 									 Battery_Voltage=float(testdict['Battery_Voltage']),
# 									 Voltage_Boost_Injector_control=float(testdict['Voltage_Boost_Injector_control']),
# 									 Pump_Adaptation_1=float(testdict['Pump_Adaptation_1']),
# 									 Waste_Gate_Adapatation_1=float(testdict['Waste_Gate_Adapatation_1']),
# 									 Waste_Gate_Adapatation_2=float(testdict['Waste_Gate_Adapatation_2']),
# 									 Vehicle_Speed=float(testdict['Vehicle_Speed']))
# 			#print("---")
# 			session.add(new_row)
# 			x = x+1
# 			if(x % 50 == 0):
# 				session.commit()
# 		session.commit()
# 		print("CSV ADDED")
#
# #session.commit()
#
#
#
#
#
#
#
#
#
