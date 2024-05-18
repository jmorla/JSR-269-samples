echo "Compiling ReportProcessor and Generate classes"
javac com/jmorla/ReportProcessor.java com/jmorla/Generate.java -d out

echo "Running processor"
javac -cp target/ -processor com.jmorla.ReportProcessor com/jmorla/Company.java