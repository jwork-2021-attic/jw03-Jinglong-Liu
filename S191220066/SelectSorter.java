package S191220066;

public class SelectSorter implements Sorter{
    private String plan = "";
    int[] nums;
    @Override
    public void load(int[] elements){
        nums = elements;
    }
    @Override
    public void sort(){
        selectSort(nums);
    }
    @Override
    public String getPlan(){
        return plan;
    }
    private void selectSort(int []nums){
        for(int i = 0; i < nums.length; i++){           
            int minIndex = i;          
            for(int j = i + 1; j < nums.length; j++){             
                if(nums[j] < nums[minIndex]){                   
                    minIndex = j;             
                }           
            }           
            swap(nums, i, minIndex);         
        }   
    }
    private void swap(int []a,int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        plan += "" + a[i] + "<->" + a[j] + "\n";
    }
}
