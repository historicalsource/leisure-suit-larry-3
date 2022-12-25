;**
;**	Logics for room 380 -- Weight Machines
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 17, 1990
;**

(script#				380)

(public
	rm380				0
)


(procedure
	StartExercising
	NormalJock
	UpdateCounters
)


(define	vRoom							380)
	(define	lWeight				0)
	(define	lBar					1)
	(define	lPullupHandles		2)

(define	vWeightRoom1 				381)
	(define	lMisc					0)
		(define	cLegCurlBar	2)		
	(define	lPreBenchPress	  	1)
	(define	lBenchPressing		2)
	(define	lPostBenchPress	3)
	(define	lBarPull				4)
	(define	lPrePullups			5)
	(define	lDoingPullups		6)
	(define	lCenterWeight		7)
	(define	lLegLifts			8)

(define	vWeightRoom2				382)
(define	vWeightRoom3				383)
(define	vWeightRoom4				384)
	(define	lBigLegs				0)
	(define	lBackOfHead			1)
	(define	lCenterWeightBIG	2)

(define	vLhulk1						385)
(define	vLhulk2						386)
	(define	lRaiseArms			0)
	(define	lPulsatePecs		1)
	(define	lSmile				2)
	(define	lFlexLeftBicep		3)
	(define	lFlexRightBicep	4)
	(define	lPoseEast			5)
	(define	lPoseWest			6)

(define	vLhulk3						387)


(define	sRocky						380)


(define	nearBenchPress				(& (ego onControl) cGreen))
(define	nearLegCurls				(& (ego onControl) cCyan))
(define	nearPullups 				(& (ego onControl) cRed))
(define	nearBarPull 				(& (ego onControl) cMagenta))
(define	leavingRoom 				(& (ego onControl) cBrown))


(define 	benchPressBigEgoX			156)
(define 	benchPressBigEgoY			024)

(define 	legCurlBigEgoPosn		155 80)

(define 	dumbbellX					155)
(define 	dumbbellY					034)

(define 	centerWeightX			 	153)
(define 	centerWeightY			 	166)
(define 	centerWeightUpPosn	153 136)


(enum												;**	RoomScript state values
	intro
	startLegLifts
	waitingToLegLift
	liftingLeg
	legAtTop
	movelegDown
	legLiftSafetyBuffer
	legCurlsDone
	oneLegLiftWeightIsDown
	legCurlsAllDone
	startBenchPress
	holdWeights
	waitingToBenchPress
	liftingBar
	allBarsAtTop
	lowerBar
	benchPressSafetyBuffer
	benchPressDone
	BPdone
	startPullups
	atPullupsStation
	onPullupStation
	waitingToPullup
	pullingUp
	pullupAtTop
	startPullupDown
	pullupSafetyBuffer
	pullupsDone
	startBarPull
	atBarPull
	waitingToBarPull
	pullingBar
	barAtBottom
	moveBarUp
	barPullSafetyBuffer
	barPullDone
	oneBarPullWeightIsDown
	twoBarPullWeightsAreDown
	barPullAllDone
	startHulk
	boyOhBoy
	loopThruHulkView1
	delayView1
	hulkRaiseArms
	hulkPulsatePecs
	pulsatingPecs
	hulkSmile
	unSmile
	hulkFlexLeftBicep
	hulkUnFlexLeftBicep
	hulkPoseEast
	hulkUnPoseEast
	hulkFlexRightBicep
	hulkUnFlexRightBicep
	hulkPoseWest
	hulkUnPoseWest
	deflate1
	deflate2
	deflate3
	hulkDone
)


(local
	messageNum
	loopNum 
	touchedBottom
	waitingToHulk 
	previousX
	previousY
	previousPri

	BenchPressMax		
	LegCurlsMax			
	PullupsMax			
	BarPullMax			

	curMachine
		(enum
			NoMachine
			BarPull
			Pullups
			BenchPress
			LegCurls
		)
	workOutState
		(enum 
			NOT_WORKING_OUT					;**	not working out
			MOVING_UP
			MOVING_DOWN
			WAITING_FOR_UP						;**	waiting for an  up keystroke
			WAITING_FOR_DOWN					;**	waiting for a down keystroke
		)
)


(synonyms
	(use do exercise )
)


(instance rm380 of Room
	(properties
		picture		380
		horizon		1
		east			370
	)

	(method (init &tmp s)
		(if (not egoIsHunk)
			(Load SOUND sRocky)
			(Load VIEW vWeightRoom3)
			(Load VIEW vWeightRoom4)
			(Load VIEW vLhulk1)
			(Load VIEW vLhulk2)
			(Load VIEW vLhulk3)
		)

		(super init)

		(addToPics
			add 				atpPullupHandles,
			doit
		)

		(aBigEgo				init)
		(aActor1				init)
		(aCenterWeight		init)
		(aRoundBar			init)
		(aExtraBar			init)
		(aDumbbell			init)
		(aBarPullBarView	init)
		(aLegCurlBar		init)

		(self
			setScript 		RoomScript
		)

		(= s (+ (/ machineSpeed 5) 5))
		(if debugging			 			;**	save Carlos' finger for other things
			(= s 5)
		)
		(= BarPullMax		s)			
		(= PullupsMax		s)
		(= BenchPressMax	s)
		(= LegCurlsMax		s)

		(NormalEgo facingLeft (+ vLsweats egoIsHunk))
		(ego
			posn				270 175,
			setPri			9,
			viewer			egoViewer,
			init,
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if leavingRoom
			(curRoom newRoom 370)
		)
	)


	(method (handleEvent event)
		(super handleEvent event)

		(if (event claimed) (return))

		(if (and curMachine
					(== (event type) keyDown))
			(cond
				((or	(== (event message) UPARROW)
						(== (event message) HOMEKEY)
						(== (event message) PAGEUP))
					(cond
						((== workOutState WAITING_FOR_UP)
							(self cue)
						)
						((== workOutState MOVING_DOWN)
							(++ state)			;**	He doesn't get credit for this.
							(self cue)
						)
					)
				)
				((or	(== (event message) DOWNARROW)
				 		(== (event message) ENDKEY)
				 		(== (event message) PAGEDOWN))
					(cond
						((== workOutState WAITING_FOR_DOWN)
							(self cue)
						)
						((== workOutState MOVING_UP)
							(++ state)			;**	He doesn't get credit for this.
							(self cue)
						)
					)
				)
			)
		)


		(if (!= (event type) saidEvent) (return))


		(cond
			((or	(Said 'rise, (stand<up), (get<off, up)')
					(Said '(get<off, up), exit, stop, quit')
					(Said 'leave/chair'))
				(switch curMachine
					(NoMachine
						(YouAre)
					)
					(LegCurls
						(RoomScript changeState legCurlsDone)
					)
					(BenchPress
						(RoomScript changeState benchPressDone)
					)
					(Pullups
						(RoomScript changeState pullupsDone)
					)
					(BarPull
						(RoomScript changeState barPullDone)
					)
				)
			)

			((Said 'sit, (work<out), workout')
				(cond
					(curMachine
						(Print "You are already exercising!")
					)
					(nearLegCurls
						(if (>= LegCurlsCount LegCurlsMax)
							(Print "You've done enough leg curls!")
						else
							(self changeState startLegLifts)
						)
					)
					(nearBenchPress
						(if (>= BenchPressCount BenchPressMax)
							(Print "You've bench pressed a ton already! Enough!")
						else
							(self changeState startBenchPress)
						)
					)
					(nearPullups
						(if (>= PullupsCount PullupsMax)
							(Print "You've done enough pull ups for any man!")
						else
							(self changeState startPullups)
						)
					)
					(nearBarPull
						(if (>= BarPullCount BarPullMax)
							(Print "You've pulled that bar enough!")
						else
							(self changeState startBarPull)
						)
					)
					(else
						(Print "There are four separate workout stations on
							Fat City's exercise equipment:
							the Leg Curl bench, 
							the Bench Press weight-lifting bench, 
							the Pullup station, 
							and the Pull Bar.")
						(Print "You must be near the station you wish to use.")
					)
				)
			)

			((or	(Said 'use/machine<pull, bar')
					(Said 'use/bar, pull')
					(Said 'use/pull<bar')
					(Said 'pull/bar'))
				(cond
					(curMachine
						(Print "You are already exercising!")
					)
					((>= BarPullCount BarPullMax)
						(Print "You've pulled that bar enough!
							How about something else?")
					)
					(nearBarPull
						(self changeState startBarPull)
					)
					(else
						(Print "Move until you are near the bench on the 
							left side of the exercise machine.")
					)
				)
			)

			((or	(Said 'use/machine<curl')
					(Said 'curl')
					(Said 'use/curl'))
				(cond
					(curMachine
						(Print "You are already exercising!")
					)
					((>= LegCurlsCount LegCurlsMax)
						(Print "You've done enough leg curls for any hunk!
							Why not try another station?")
					)
					(nearLegCurls
						(self changeState startLegLifts)
					)
					(else
						(Print "Move until you are at the far side of the bench 
							on the right side of the exercise machine.")
					)
				)
			)

			((or	(Said 'use/machine<press, bench')
					(Said 'press, dumbbell')
					(Said 'bench<press/')
					(Said 'get<on/bench')
					(Said 'lift, use/press, weight, dumbbell'))
				(cond
					(curMachine
						(Print "You are already exercising!")
					)
					((>= BenchPressCount BenchPressMax)
						(Print "You've lifted enough iron for any man.
							Why not try another station?")
					)
					(nearBenchPress
						(self changeState startBenchPress)
					)
					(else
						(Print "Move until you are on the near side of the bench 
							at the right side of the exercise machine.")
					)
				)
			)

			((or	(Said 'use/machine<up, pullup, pull')
					(Said 'use/up<pull')
					(Said 'pullups')
					(Said '(up<pull)')
					(Said 'use/pullup'))
				(cond
					(curMachine
						(Print "You are already exercising!")
					)
					((>= PullupsCount PullupsMax)
						(Print "You've worked out enough on this machine!
							Why not try another station?")
					)
					(nearPullups
						(self changeState startPullups)
					)
					(else
						(Print "Move until you are at the front center
							of the exercise machine.")
					)
				)
			)

			((Said 'sweat')
				(if curMachine
					(YouAre)
				else
					(Print "Ok. Use one of the machines.")
				)
			)

			((Said 'look>')
				(cond
					((Said '[/machine, station, room]')
						(Print "There are four separate workout stations on
							Fat City's exercise equipment:
							the \"Leg Curl\" bench, 
							the \"Bench Press\" weight-lifting dumbbell bench, 
							the \"Pullup\" station, 
							and the \"Pull Bar.\"")
					)
				)
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 1 red)

		(switch (= state newState)

			(intro
			)


			;**		LegCurls
			;**	Lying down on the bench on the far right.

			(startLegLifts			
				(StartExercising LegCurls)
				(ego
					view				vWeightRoom1,
					posn				201 135,
					setLoop			lLegLifts,
					cel				0,
					cycleSpeed		1,
				)
				(aActor1
					setStep			3 5,
					posn				155 45,
					view				vWeightRoom4,
					setLoop			lCenterWeightBIG,
					setPri			12,
				)
				(aBigEgo							;**	back of larry's head
					view				vWeightRoom4,
					setLoop			lBackOfHead,
					setPri			13,
					posn				legCurlBigEgoPosn,
					stopUpd,
				)
				(aLegCurlBar		hide)
				(self cue)
			)

			(waitingToLegLift
				(= touchedBottom TRUE)
				(= workOutState WAITING_FOR_UP)
			)

			(liftingLeg
				(= workOutState MOVING_UP)
				(ego
					cel				0,
					setCycle			EndLoop,
				)
				(aActor1
					setMotion		MoveTo 155 15 self,
				)
				(aCenterWeight
					setMotion		MoveTo centerWeightUpPosn ,
				)
			)

			(legAtTop
				(= workOutState WAITING_FOR_DOWN)
				(if touchedBottom
					(if (== (++ LegCurlsCount) LegCurlsMax) 
						(Printf "Congratulations! You've done %d leg curls." 
							LegCurlsCount)
					)
					(UpdateCounters)
				)
			)

			(movelegDown
				(= workOutState MOVING_DOWN)
				(ego
					setCycle			BegLoop,
				)
				(aActor1
					setMotion		MoveTo 155 50 self,
				)
				(aCenterWeight
					setMotion		MoveTo centerWeightX centerWeightY,
				)
				(= state (- waitingToLegLift 1))
			)

			(legLiftSafetyBuffer
			)


			;**	Jump to here when finished with leg lifts

			(legCurlsDone
				(User
					mapKeyToDir		TRUE,
					canInput			FALSE,
					canControl		FALSE,
				)
				(aCenterWeight
					setMotion		MoveTo centerWeightX centerWeightY self,
				)
				(aActor1
					setMotion		MoveTo 155 50 self,
				)
			)

			(oneLegLiftWeightIsDown
			)

			(legCurlsAllDone
				(aLegCurlBar		show)
				(aCenterWeight
					stopUpd,
				)
				(NormalJock)
			)



			;**		BenchPress
			;**	Lie on back on the bench on the far right.

			(startBenchPress
				(StartExercising BenchPress)
				(ego
					cycleSpeed		1,
					view				vWeightRoom1,
					posn				216 163,
					setLoop			lPreBenchPress,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(holdWeights
				(aRoundBar			hide)
				(aBigEgo
					view				vWeightRoom2,
					cel				0,
					setPri			13,
					posn				benchPressBigEgoX benchPressBigEgoY,
				)
				(aDumbbell			hide)
				(aActor1
					view				vRoom,
					setLoop			lWeight,
					setStep			7 7,
					setPri			14,
					posn				dumbbellX dumbbellY,
				)
				(ego
					setPri			13,
					setLoop			lBenchPressing,
					posn				219 159,
				)
				(self cue)
			)

			(waitingToBenchPress
				(= touchedBottom TRUE)
				(= workOutState WAITING_FOR_UP)
			)

			(liftingBar
				(= workOutState MOVING_UP)
				(ego
					cel				0,
					setCycle			EndLoop self,
				)
				(aActor1
					setMotion		MoveTo 155 20 ,
				)
				(aBigEgo
					setCycle			EndLoop ,
				)
			)

			(allBarsAtTop
				(= workOutState WAITING_FOR_DOWN)
				(if touchedBottom
					(if (== (++ BenchPressCount) BenchPressMax) 
						(Printf "Congratulations! You've pumped over %d pounds." 
							(* 100 BenchPressCount))
					)
					(UpdateCounters)
				)
			)

			(lowerBar
				(= workOutState MOVING_DOWN)
				(ego
					setCycle			BegLoop self,
				)
				(aActor1
					setMotion		MoveTo 155 34 ,
				)
				(aBigEgo
					setCycle			BegLoop ,
				)
				(= state (- waitingToBenchPress 1))
			)
		
			(benchPressSafetyBuffer
			)


			;**	Jump to here when finished with bench press

			(benchPressDone
				(User
					mapKeyToDir		TRUE,
					canInput			FALSE,
					canControl		FALSE,
				)
				(aBigEgo
					posn				1234 1234,
					setMotion		FALSE,
				)
				(aActor1
					posn				dumbbellX dumbbellY,
					setMotion		FALSE,
					stopUpd,
				)
				(aRoundBar			show)
				(aDumbbell			show)
				(ego
					posn				216 163,
					setLoop			lPostBenchPress,
					setCel			0,
					setCycle			EndLoop self,
				)
			)

			(BPdone
				(NormalJock)
			)


			;**		Pullups
			;** 	Holding handles and lifting body on middle front machine

			(startPullups
				(ego
					illegalBits		0,
					viewer			FALSE,
					setPri			13,
					setMotion		MoveTo 156 171 self
				)
			)

			(atPullupsStation
				(ego
					view				vWeightRoom1,
					posn				156 146,
					setLoop			lPrePullups,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(onPullupStation
				(StartExercising Pullups)
				(ego
					posn				165 143,
					setPri			13,
					setLoop			lDoingPullups,
					cel				0,
					cycleSpeed		2,
				)
				(aBigEgo
					view				vWeightRoom4,
					setLoop			lBigLegs,
					posn				302 1,
				)
				(self cue)
			)

			(waitingToPullup
				(= touchedBottom TRUE)
				(= workOutState WAITING_FOR_UP)
			)

			(pullingUp
				(= workOutState MOVING_UP)
				(ego
					setCycle			EndLoop,
				)
				(aBigEgo
					setMotion		MoveTo 302 -52 self,
				)
			)

			(pullupAtTop
				(= workOutState WAITING_FOR_DOWN)
				(if touchedBottom
					(if (== (++ PullupsCount) PullupsMax) 
						(Printf "Congratulations! You've done %d pull ups." PullupsCount)
					)
					(UpdateCounters)
				)
			)

			(startPullupDown
				(= workOutState MOVING_DOWN)
				(ego
					setCycle			BegLoop,
				)
				(aBigEgo
					setMotion		MoveTo 302 1 self,
				)
				(= state (- waitingToPullup 1))
			)

			(pullupSafetyBuffer
			)


			;**	Jump to here when pull ups are done

			(pullupsDone
				(User
					mapKeyToDir		TRUE,
				)
				(= previousX 156)
				(= previousY 175)
				(NormalJock)
			)


			;**		BarPull
			;**	west station, recline on bench and pull down on big handle.

			(startBarPull
				(StartExercising BarPull)
				(ego
					cycleSpeed		1,
					view				vWeightRoom1,
					posn				131 165,
					setLoop			lPreBenchPress,
					cel				0,
					setCycle			EndLoop self,
					setPri			13,
				)
			)

			(atBarPull
				(ego
					setLoop			lBarPull,
					setCel			0,
					posn				126 165,
				)
				(aBigEgo
					cycleSpeed		1,
					view				vWeightRoom3,
					posn				76 115,
					setPri			13,
					cel				0,
				)
				(aActor1
					setStep			3 5,
					posn				155 45,
					view				vWeightRoom4,
					setLoop			lCenterWeightBIG,
					setPri			12,
				)
				(aExtraBar			hide)
				(aBarPullBarView	hide)
				(self cue)
			)

			(waitingToBarPull
				(= touchedBottom TRUE)
				(= workOutState WAITING_FOR_DOWN)
			)

			(pullingBar
				(= workOutState MOVING_DOWN)
				(ego
					setCycle			EndLoop,
				)
				(aBigEgo
					setCycle			EndLoop,
				)
				(aActor1
					setMotion		MoveTo 155 15 self,
				)
				(aCenterWeight
					setMotion		MoveTo centerWeightUpPosn ,
				)
			)

			(barAtBottom
				(= workOutState WAITING_FOR_UP)
				(if touchedBottom
					(if (== (++ BarPullCount) BarPullMax) 
						(Printf "Congratulations! You've done %d bar pulls." BarPullCount)
					)
					(UpdateCounters)
				)
			)

			(moveBarUp
				(= workOutState MOVING_UP)
				(ego
					setCycle			BegLoop,
				)
				(aBigEgo
					setCycle			BegLoop,
				)
				(aActor1
					setMotion		MoveTo 155 50 self,
				)
				(aCenterWeight
					setMotion		MoveTo centerWeightX centerWeightY ,
				)
				(= state (- waitingToBarPull 1))
			)

			(barPullSafetyBuffer
			)


			;**	Jump to here when finished with bar pull

			(barPullDone
				(User
					mapKeyToDir		TRUE,
					canInput			FALSE,
					canControl		FALSE,
				)
				(aCenterWeight
					setMotion		MoveTo centerWeightX centerWeightY self,
				)
				(aActor1
					setMotion		MoveTo 155 50 self,
				)
				(aBarPullBarView	
					show,
					stopUpd,
				)
				(aExtraBar
					show,
					stopUpd,
				)
				(aBigEgo
					posn				999 999,
				)
				(ego
					posn				131 165,
					setLoop			lPostBenchPress,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(oneBarPullWeightIsDown
			)

			(twoBarPullWeightsAreDown
			)

			(barPullAllDone
				(aCenterWeight
					stopUpd,
				)
				(NormalJock)
			)


			;**		Larry has successfully completed the minimum standard 
			;**	requirement for each of the four stations.
			;**	Now he becomes the "incredible hunk!"

			(startHulk
				(= egoIsHunk (- vLhunk vLarry)) ;**	diff of 20 between fat & hunk views
				(= waitingToHulk TRUE)
				(= normalEgoView vLhunk)
				(NormalEgo facingDown vLsweatsHunk)
				(HandsOff)
				(delay 3)
			)

			(boyOhBoy
				(Print "Boy, oh boy! This exercise stuff really works!
					Why, you look and feel like a new man!")
				(music
					number			sRocky,
					loop				bgMusicLoops,
					play ,			
				)
				(= loopNum 0)
				(NormalEgo facingDown vLsweats)	;**	couldn't be hunk if here
				(HandsOff)
				(= oldGameSpeed (theGame setSpeed 6))
				(delay 3)
			)

			(loopThruHulkView1
				(ego
					illegalBits		cBrown,
					ignoreActors,
					view				vLhulk1,
					setLoop			loopNum,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(delayView1
				(cond
					((== 0 loopNum)
						(Print "Hey! What's happening!?")
					)
					((== 3 loopNum)
						(Print "Whoa!")
					)
				)
				(if (>= 4 (++ loopNum))
					(-= state 2)				;**	go back for another loop
				)
				(= cycles 11)
			)

			(hulkRaiseArms			
				(Print "Well, well! Look at this. It's \"The Incredible Dork!\"")
				(ego
					view				vLhulk2,
					loop				lRaiseArms,
					cel				0,
					setCycle			EndLoop ,
				)
				(= cycles 18)
			)

			(hulkPulsatePecs		
				(ego
					loop				lPulsatePecs,
					cel				0,
					setCycle			Forward,
				)
				(= cycles 33)
			)

			(pulsatingPecs
				(Print "There they are!\nThe \"Pulsating Pectorals!!\"")
				(= cycles 33)
			)

			(hulkSmile				
				(ego
					loop				lSmile,
					cel				0,
					setCycle			EndLoop ,
				)
				(= cycles 11)
			)

			(unSmile
				(Print "You could learn to like this!")
				(ego
					setCycle			BegLoop ,
				)
				(= cycles 11)
			)

			(hulkFlexLeftBicep		
				(ego
					loop				lFlexLeftBicep,
					cel				0,
					setCycle			EndLoop ,
				)
				(= cycles 15)
			)

			(hulkUnFlexLeftBicep		
				(ego
					setCycle			BegLoop ,
				)
				(= cycles 11)
			)

			(hulkPoseEast	
				(Print "Maybe you could enter one of those contests!")
				(ego
					loop				lPoseEast,
					cel				0,
					setCycle			EndLoop ,
				)
				(= cycles 22)
			)

			(hulkUnPoseEast			
				(ego
					setCycle			BegLoop ,
				)
				(= cycles 11)
			)

			(hulkFlexRightBicep	
				(ego
					loop				lFlexRightBicep,
					cel				0,
					setCycle			EndLoop ,
				)
				(= cycles 15)
			)

			(hulkUnFlexRightBicep		
				(ego
					setCycle			BegLoop ,
				)
				(= cycles 11)
			)

			(hulkPoseWest			
				(ego
					loop				lPoseWest,
					cel				0,
					setCycle			EndLoop ,
				)
				(= cycles 22)
			)
			
			(hulkUnPoseWest			
				(ego
					setCycle			BegLoop ,
				)
				(= cycles 11)
			)

			(deflate1
				(ego
					view				vLhulk3,
					loop				0,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(deflate2
				(Print "Oops. Your new body has sprung a leak!")
				(ego
					loop				1,
					setCycle			Forward,
				)
				(= cycles 33)
			)

			(deflate3
				(ego
					loop				2,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(hulkDone
				(NormalEgo facingRight vLsweatsHunk)	;**	all he can be if here
				(ego viewer egoViewer)
				(theGame changeScore 100)
				(Print "Oh, well.
					Who would want to be that \"pumped up\" anyway?
					At least you're no longer overweight; 
					why, just take a look at yourself.
					That gut of yours is gone and just look at those muscles!
					What a hunk!!"
				)
				(music
					number			sFatCityRegion,
					loop				bgMusicLoops,
					play ,			
				)
				(theGame setSpeed oldGameSpeed)
			)
		)
	)
)


(instance atpPullupHandles of PicView
	(properties
		view					vRoom
		loop					lPullupHandles
;		cel					cPullupHandles
		x						165 
		y						143
		priority				9
		signal				ignrAct
	)
)
(instance aRoundBar of View
	(properties
		view				vWeightRoom1
		loop				lMisc
		x					201
		y					142
		signal  			ignrAct
	)

	(method (init)
		(super init)
		(self
			setPri			10,
			stopUpd,
		)
	)
)
(instance aBarPullBarView of View
	(properties
		view				vRoom
		loop				lBar
		x					30
		y					63
		signal			ignrAct
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			stopUpd,
		)
	)
)
(instance aLegCurlBar of View
	(properties
		view				vWeightRoom1
		loop				lMisc
		cel				cLegCurlBar
		x					226
		y					164
		signal  			ignrAct
	)							

	(method (init)
		(super init)
		(self
			setPri			9,
			stopUpd,
		)
	)
)
(instance aDumbbell of View
	(properties
		view				vRoom
		loop				lWeight
		x					dumbbellX
		y					dumbbellY
		signal  			ignrAct
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			stopUpd,
		)
	)
)
(instance aExtraBar of View
	(properties
		view				vWeightRoom1
		loop				lMisc
		cel				1
		x					128
		y					138
		signal  			ignrAct
	)

	(method (init)
		(super init)
		(self
			setPri			9,
			stopUpd,
		)
	)
)


(instance aActor1 of Actor
	(properties
		view				vRoom
		loop				lWeight
		x					dumbbellX
		y					999
		illegalBits		0
		signal			ignrAct
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			ignoreHorizon,
			stopUpd,
		)
	)
)
(instance aCenterWeight of Actor
	(properties
		view				vWeightRoom1
;		loop				lCenterWeight
		x					centerWeightX
		y					centerWeightY
		illegalBits		0
		signal			ignrAct
	)

	(method (init)
		(super init)
		(self
			setStep			1 5,
			setLoop			lCenterWeight,
			setPri			11,
			stopUpd,
		)
	)
)
(instance aBigEgo of Actor
	(properties
		view				vWeightRoom2
		x					benchPressBigEgoX
		y					999
		illegalBits		0
		signal			ignrAct
	)

	(method (init)
		(super init)
		(self
			setPri			14,
			setStep			6 6,
			ignoreHorizon,
			stopUpd,
		)
	)
)


(procedure (StartExercising onWhat)
	(Ok)
	(HandsOff)
	(= curMachine onWhat)
	(= previousX 	(ego x))
	(= previousY 	(ego y))
	(= previousPri (ego priority))
	(switch (++ messageNum)
		(1 (Print "Use the cursor keys until you work up a sweat."))
		(2	(Print "No pain, no gain!"))
		(3 (Print "You can do it!"))
		(4 (Print "Smile, this is supposed to be fun."))
		(5 (Print "Who booked this gig?")
			(= messageNum 0)
		)
	)
	(User
		mapKeyToDir		FALSE,
		canInput			TRUE,
	)
	(ego
		viewer			FALSE,
		illegalBits		0,
		ignoreActors,
	)
)

(procedure (NormalJock)
	(= curMachine NoMachine)
	(= workOutState NOT_WORKING_OUT)
	(SetFlg needsShower)
	(SetFlg needsSoap)
	(SetFlg needsDeodorant)
	(NormalEgo facingDown (+ vLsweats egoIsHunk))
	(ego
		posn				previousX previousY,
		setPri			previousPri,
		viewer			egoViewer,
	)
	(aBigEgo
	 	cycleSpeed		0,
		posn				1234 1234,
		setMotion		FALSE,
		stopUpd,
	)
	(if (and (>= BarPullCount		BarPullMax)
				(>= PullupsCount		PullupsMax)
				(>= BenchPressCount	BenchPressMax)
				(>= LegCurlsCount 	LegCurlsMax))
		(HandsOff)
		(RoomScript changeState startHulk)
	)
)
(instance egoViewer of Code
	(method (doit)
		(cond
			((< (ego y) 172)					(ego setPri 4))
			((& (ego onControl) cBlue)		(ego setPri	9))
			(else									(ego setPri	RELEASE))
		)
	)
)
(procedure (UpdateCounters &tmp [string 11])
	(= touchedBottom FALSE)
	(Display (Format @string " %-2d__%-2d__%-2d__%-2d "
		BarPullCount 	
		PullupsCount 	
		BenchPressCount
		LegCurlsCount	)
			#p_at			122 82
			#p_font		SANS_SERIF_8
			#p_color		ltRed
			#p_back		black
	)
)

