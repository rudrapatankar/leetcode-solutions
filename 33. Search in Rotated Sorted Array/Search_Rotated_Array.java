class Solution {
    public int search(int[] nums, int target) 
    {
       int lb =0;
       int ub=nums.length-1;
       int mid = lb+(ub-lb)/2;
       while(lb<ub)
       {
        mid = lb+(ub-lb)/2;
        if(nums[mid]>nums[ub])
        {
            lb=mid+1;
        }
        else
        {
            ub=mid;
        }
       }
       int min_index=lb;

       if (target >= nums[min_index] && target <= nums[nums.length - 1]) {
            lb = min_index;
            ub = nums.length - 1;
        } else {
            lb = 0;
            ub = min_index - 1;
        }

        while (lb <= ub) {
            mid = lb + (ub - lb) / 2;

            if (nums[mid] == target) {
                return mid;
            } 
            else if (nums[mid] < target) {
                lb = mid + 1;
            } 
            else {
                ub = mid - 1;
            }
        }
       return -1;
    }
}