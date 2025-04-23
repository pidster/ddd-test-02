package com.lakesidemutual.claimprocessing.interfaces.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lakesidemutual.claimprocessing.application.service.ClaimService;
import com.lakesidemutual.claimprocessing.application.service.PolicyHolderService;
import com.lakesidemutual.claimprocessing.domain.model.ClaimStatus;

@Controller
public class HomeController {

    private final ClaimService claimService;
    private final PolicyHolderService policyHolderService;
    
    @Autowired
    public HomeController(ClaimService claimService, PolicyHolderService policyHolderService) {
        this.claimService = claimService;
        this.policyHolderService = policyHolderService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Lakeside Mutual - Claim Processing");
        model.addAttribute("policyHolders", policyHolderService.getAllPolicyHolders());
        model.addAttribute("allClaims", claimService.getAllClaims());
        model.addAttribute("submittedClaims", claimService.getClaimsByStatus(ClaimStatus.SUBMITTED));
        model.addAttribute("reviewClaims", claimService.getClaimsByStatus(ClaimStatus.UNDER_REVIEW));
        model.addAttribute("approvedClaims", claimService.getClaimsByStatus(ClaimStatus.APPROVED));
        return "home";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "Claims Dashboard");
        model.addAttribute("submittedCount", claimService.getClaimsByStatus(ClaimStatus.SUBMITTED).size());
        model.addAttribute("reviewCount", claimService.getClaimsByStatus(ClaimStatus.UNDER_REVIEW).size());
        model.addAttribute("approvedCount", claimService.getClaimsByStatus(ClaimStatus.APPROVED).size());
        model.addAttribute("rejectedCount", claimService.getClaimsByStatus(ClaimStatus.REJECTED).size());
        model.addAttribute("paidCount", claimService.getClaimsByStatus(ClaimStatus.PAID).size());
        return "dashboard";
    }
    
    @GetMapping("/getting-started")
    public String gettingStarted(Model model) {
        model.addAttribute("title", "Getting Started - Lakeside Mutual");
        return "getting-started";
    }
}
