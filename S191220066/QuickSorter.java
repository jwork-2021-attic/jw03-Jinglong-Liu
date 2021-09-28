package S191220066;
public class QuickSorter implements Sorter{
    private String plan = "";
    int[] nums;
    @Override
    public void load(int[] elements){
        nums = elements;
    }
    @Override
    public void sort(){
        quickSort(nums, 0, nums.length-1);
    }
    @Override
    public String getPlan(){
        return plan;
    }
    
    
    public void quickSort(int nums[],int start,int end) {        
        int pivot = nums[start];        
        int i = start;        
        int j = end;        
        while (i<j) {            
            while ((i<j)&&(nums[j]>pivot)) {                
                j--;            
            }            
            while ((i<j)&&(nums[i]<pivot)) {                
                i++;            
            }            
            if ((nums[i]==nums[j])&&(i<j)) {                
                i++;            
            } else {           
                swap(nums,i,j);         
            }        
        }        
        if (i-1>start){
            quickSort(nums,start,i-1);  
        }       
        if (j+1<end){
            quickSort(nums,j+1,end);   
        }        
    }  
    private void swap(int []a,int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        plan += "" + a[i] + "<->" + a[j] + "\n";
    }
}
