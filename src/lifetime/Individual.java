/**
 * 
 */
package lifetime;

/**
 * @author Gloria
 *
 */
public class Individual {
	  static int defaultGeneLength = 20;
	  public Chromosome chrom;
	  private double x1 , x2; // 个体表现型
	  //基因型chromosome由 (x1 , x2)编码而成
	  
//	  private byte[] genes;
	  private double mFitness =0;
	  private int mLifetime =0;
	  private int mAge =0;
	  final int MINLIFETIME = 1;
	  final int MAXLIFETIMES = 7;
	  
	  public Individual(Individual dad, Individual mom, double UniformRate){
		  chrom = new Chromosome(defaultGeneLength);
		  double f = 0;
		  do{
			  for(int i = 0; i < defaultGeneLength; i++)
			  {
				  if (Math.random() <= UniformRate)
				  {
					  if (Math.random() <= 0.5)
					  {
						  chrom.setGene(i, i, dad.chrom.getGene(i, i));
					  }
					  else
					  {
						  chrom.setGene(i, i, mom.chrom.getGene(i, i));
					  }
				  }
			  }
		      String s = chrom.getGene(0, defaultGeneLength - 1);
		      //System.out.println(s);
		      f = decodeGene(s);
		      break;
		  }while(f < -5 || f > 5);

	  }
	  
	  public Individual(){
		  chrom = new Chromosome(defaultGeneLength);
		  generateIndividual();
	  }  
	  
	  public void mutate(double utationRate){
		  
		  for(int i=0;i<defaultGeneLength;i++){
			  if(Math.random() <= utationRate){
				  String s = chrom.getGene(i, i);
				  if (s.equals("1"))
				  {
					  chrom.setGene(i, i, "0");
				  }
				  else
				  {
					  chrom.setGene(i, i, "1");
				  }
			  }
		  }		  
	  }
	  
	  //生成基因型个体
	  public  void generateIndividual(){
		  x1 = Math.random() * (Math.random() * 2 > 1 ? -5 : 5);
		  x2 = Math.random() * (Math.random() * 2 > 1 ? -5 : 5);
				
			//同步编码和适应度
		  coding();
		  calcFitness();
			
		  System.out.println("generating " + this.toString() + " " + this.getPhenotype());
	  }
	  

	  //获取个体的适应度值
	  public double getFitness(){
		  //if(mFitness == 0){
			  mFitness = calcFitness();
		  //}
		  return mFitness;
	  }
	  //获取个体的生命周期
	  public int getLifetime(double bestFit, double avgFit, double worstFit){
		  double fitness = getFitness();
		  if (fitness <= avgFit)
		  {
			  mLifetime = (int) (MINLIFETIME+1/2*(bestFit-worstFit)*(fitness-worstFit)/(avgFit-worstFit));
		  }
		  else
		  {
			  mLifetime = (int) (1/2*(MINLIFETIME+MINLIFETIME)+1/2*(MINLIFETIME-MINLIFETIME)*(fitness-avgFit)/(bestFit-avgFit));
		  }		  
		  return mLifetime;
	  }
	  //获取个体的age值
	  public int getAge(){
		  return mAge;
	  }
	  //转化成string类型
	  
	  public String toString() {
	        return chrom.toString();
	  }
	    
	  //计算个体的适应度值
	  double calcFitness(){
		  decode();
		  return rosenbrock(x1 , x2);
	  }
	  //计算个体的age值，每执行一次进化算法，就加1
	  int calcAge(){
		  return mAge+1;		  
	  }
	  //获取表现型
	  public double getPhenotype() {
	        return ((double) Integer.parseInt(this.toString(), 2) / 1000) - 10;
	  }	
	  
	  
	  //编码
	  public void coding(){
			String code1,code2;
			code1 = codingVariable(x1);
			code2 = codingVariable(x2);
			
			chrom.setGene(0 , 9 , code1);
			chrom.setGene(10, 19 , code2);
	  }
		
	  //解码
	  public void decode(){
			String gene1,gene2;
			
			gene1 = chrom.getGene(0 , 9);
			gene2 = chrom.getGene(10 , 19);
			
			x1 = decodeGene(gene1);
			x2 = decodeGene(gene2);
	  }
	  
	  private String codingVariable(double x){
			double y = (((x + 2.048) * 1023) / 4.096);
			String code = Integer.toBinaryString((int) y);
			
			StringBuffer codeBuf = new StringBuffer(code);
			for(int i = code.length(); i< defaultGeneLength; i++)
				codeBuf.insert(0,'0');
				
			return codeBuf.toString();
	  }

	  private double decodeGene(String gene){
			int value ;
			double decode;
			value = Integer.parseInt(gene, 2);
			decode = value/1023.0*4.096 - 2.048;
			
			return decode;
	  }
			
	  //计算目标函数值
	  public void calTargetValue(){
		  decode();
		  mFitness = rosenbrock(x1 , x2);
	  }
		
		/**
		 *Rosenbrock函数:
		 *f(x1,x2) = 100*(x1**2 - x2)**2 + (1 - x1)**2
		 *在当x在[-2.048 , 2.048]内时，
		 *函数有两个极大点:
		 *f(2.048 , -2.048) = 3897.7342
		 *f(-2.048,-2.048) = 3905.926227
		 *其中后者为全局最大点。
		 */
		public static double rosenbrock(double x1 , double x2){
			double fun;
			fun = x1 * x1 + x2 * x2;
			
			return -fun;
		}
		public void dump(){
			System.out.println("x1="+x1+",x2="+x2);
		}
}
